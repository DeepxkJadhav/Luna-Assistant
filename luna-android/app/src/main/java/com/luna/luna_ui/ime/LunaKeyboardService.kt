package com.luna.luna_ui.ime

import android.inputmethodservice.InputMethodService
import android.view.View
import com.luna.luna_ui.utils.AgentLogger

/**
 * Luna's own soft keyboard — an [InputMethodService] used purely as a programmatic
 * text-injection conduit, NOT a keyboard the user types on.
 *
 * Why it exists: apps built with React Native / Flutter / canvas / game engines expose no
 * editable accessibility node, so `ACTION_SET_TEXT` (every strategy in
 * `performTextInput`) fails with `no_editable_field` — proven on Rapido, whose focused
 * search field shows 0 editable / 0 EditText / 46 generic `android.view.View`. The ONLY
 * app-level way to put text into such a field is the IME channel: when Luna is the active
 * keyboard, [android.view.inputmethod.InputConnection.commitText] delivers text to whatever
 * field has focus — exactly how Gboard reaches those apps.
 *
 * The keyboard is deliberately invisible ([onEvaluateInputViewShown] = false): Luna switches
 * to it, commits the text, and switches back to the user's keyboard, all in
 * [com.luna.luna_ui.accessibility.LunaAccessibilityService.typeViaIme]. The user never sees it.
 */
class LunaKeyboardService : InputMethodService() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        AgentLogger.Auto.i("LunaKeyboardService created")
    }

    override fun onDestroy() {
        if (instance === this) instance = null
        super.onDestroy()
    }

    // A field is bound to us — return a zero-footprint placeholder; we never show a keyboard.
    override fun onCreateInputView(): View = View(this)

    // Never render a visible keyboard: we only ever commit text programmatically.
    override fun onEvaluateInputViewShown(): Boolean = false

    override fun onEvaluateFullscreenMode(): Boolean = false

    /** True when a text field is currently bound to this IME (an [InputConnection] exists). */
    fun hasConnection(): Boolean = currentInputConnection != null

    /**
     * Commit [text] into the currently focused field. Returns whether the OS accepted the
     * commit. `commitText(text, 1)` places the cursor after the inserted text.
     */
    fun injectText(text: String): Boolean {
        val ic = currentInputConnection ?: return false
        // Spec 2026-07-31 — control lock. Typing is an action Luna takes, so its echo
        // (TYPE_VIEW_TEXT_CHANGED, possibly several as the field reformats) must be
        // attributable. Without this stamp the agent typed a message and then paused
        // itself on having typed it — this path exists precisely for the apps that reject
        // node-level text entry, so it is the one carrying the most real typing.
        com.luna.luna_ui.services.ControlLockStore.shared
            .noteDispatch(com.luna.luna_ui.services.SelfActionWindow.ActionKind.TYPE)
        val ok = ic.commitText(text, 1)
        AgentLogger.Auto.i("LunaKeyboardService.injectText", mapOf("len" to text.length.toString(), "ok" to ok.toString()))
        return ok
    }

    companion object {
        @Volatile
        var instance: LunaKeyboardService? = null
            private set
    }
}
