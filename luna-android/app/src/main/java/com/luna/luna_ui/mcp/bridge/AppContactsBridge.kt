package com.luna.luna_ui.mcp.bridge

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.luna.luna_ui.contacts.ContactMatch
import com.luna.luna_ui.contacts.ContactResolver
import com.luna.luna_ui.contacts.ContactSyncService
import com.luna.luna_ui.contacts.ResolveResult
import com.luna.mcp.bridge.ContactCandidate
import com.luna.mcp.bridge.ContactResolution
import com.luna.mcp.bridge.ContactsBridge
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.atomic.AtomicBoolean

/**
 * `:app` binding for [ContactsBridge] — wires the MCP `resolve_contact` tool to
 * the 5-stage fuzzy [ContactResolver] (exact → prefix → Levenshtein → phonetic
 * → token-sort, built for STT input, with learned STT aliases).
 *
 * The resolver and sync service are Hilt singletons (they need the Room DAOs),
 * but this bridge is constructed by hand next to the other App*Bridge wirings —
 * so it reaches them through an [EntryPoint], the same pattern
 * `LunaOverlayService` uses.
 *
 * Sync strategy: the Room mirror of device contacts is refreshed once per
 * process on first resolve (idempotent upsert). The previously-built
 * ContentObserver re-sync path only arms after `syncWithPermissionCheck`, which
 * nothing calls yet — one sync per process is fresh enough for name→number.
 *
 * All failures degrade to `none`/`permission_denied` — never an exception into
 * the tool layer.
 */
class AppContactsBridge(context: Context) : ContactsBridge {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface ContactsEntryPoint {
        fun contactResolver(): ContactResolver
        fun contactSyncService(): ContactSyncService
    }

    private val appContext = context.applicationContext
    private val entryPoint by lazy {
        EntryPointAccessors.fromApplication(appContext, ContactsEntryPoint::class.java)
    }
    private val syncedThisProcess = AtomicBoolean(false)

    override suspend fun resolveContact(name: String): ContactResolution {
        val granted = ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.READ_CONTACTS,
        ) == PackageManager.PERMISSION_GRANTED
        if (!granted) {
            return ContactResolution(ContactResolution.STATUS_PERMISSION_DENIED, emptyList())
        }

        return runCatching {
            if (syncedThisProcess.compareAndSet(false, true)) {
                runCatching { entryPoint.contactSyncService().syncContacts(appContext) }
                    .onFailure {
                        syncedThisProcess.set(false) // retry on the next resolve
                        Log.w(TAG, "contact sync failed, resolving against stale mirror: ${it.message}")
                    }
            }
            ContactResolutionMapper.map(entryPoint.contactResolver().resolve(name))
        }.getOrElse {
            Log.w(TAG, "resolveContact failed: ${it.message}")
            ContactResolution(ContactResolution.STATUS_NONE, emptyList())
        }
    }

    private companion object {
        const val TAG = "AppContactsBridge"
    }
}

/**
 * Pure [ResolveResult] → [ContactResolution] mapping — JVM-tested in
 * `ContactResolutionMapperTest`, no Android dependencies.
 */
internal object ContactResolutionMapper {

    fun map(result: ResolveResult): ContactResolution = when (result) {
        is ResolveResult.AutoResolve ->
            ContactResolution(ContactResolution.STATUS_AUTO, listOf(result.match.toCandidate()))
        is ResolveResult.Disambiguate ->
            ContactResolution(ContactResolution.STATUS_DISAMBIGUATE, result.candidates.map { it.toCandidate() })
        ResolveResult.ManualEntry ->
            ContactResolution(ContactResolution.STATUS_NONE, emptyList())
    }

    private fun ContactMatch.toCandidate() = ContactCandidate(
        contactId = contactId,
        displayName = displayName,
        phoneNumber = phoneNumber,
        score = score,
    )
}
