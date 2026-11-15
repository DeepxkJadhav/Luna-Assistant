package com.luna.mcp.server

import com.luna.mcp.bridge.BrowserAction
import com.luna.mcp.bridge.BrowserBridge
import com.luna.mcp.bridge.BrowserCapture
import com.luna.mcp.bridge.ExtractedRow
import com.luna.mcp.bridge.BrowserPage
import com.luna.mcp.bridge.BrowserResult
import com.luna.mcp.bridge.BrowserSessionMode
import com.luna.mcp.bridge.FindOutcome
import com.luna.mcp.bridge.MediaBridge
import com.luna.mcp.bridge.MediaCommand
import com.luna.mcp.bridge.MediaCommandResult
import com.luna.mcp.bridge.MediaSessionSnapshot
import com.luna.mcp.bridge.NotificationBridge
import com.luna.mcp.bridge.NotificationOpResult
import com.luna.mcp.bridge.NotificationSnapshot
import com.luna.mcp.bridge.SpecialAccessState
import com.luna.mcp.bridge.SystemIntentBridge
import com.luna.mcp.bridge.SystemIntentResult
import com.luna.mcp.bridge.SystemIntentSpec

/**
 * Shared inert fakes for the assistant-plane bridges (notifications, media,
 * system intents) used by every test that builds the real server but never
 * dispatches these tools.
 */
internal object InertNotificationBridge : NotificationBridge {
    override fun accessState(): SpecialAccessState = error("not dispatched")
    override suspend fun activeNotifications(): List<NotificationSnapshot> = error("not dispatched")
    override suspend fun fireAction(key: String, actionTitle: String, replyText: String?): NotificationOpResult =
        error("not dispatched")
    override suspend fun dismiss(key: String): NotificationOpResult = error("not dispatched")
}

internal object InertMediaBridge : MediaBridge {
    override fun accessState(): SpecialAccessState = error("not dispatched")
    override suspend fun activeSessions(): List<MediaSessionSnapshot> = error("not dispatched")
    override suspend fun sendCommand(packageName: String?, command: MediaCommand): MediaCommandResult =
        error("not dispatched")
}

internal object InertSystemIntentBridge : SystemIntentBridge {
    override fun dispatch(spec: SystemIntentSpec): SystemIntentResult = error("not dispatched")
}

/**
 * Inert browser bridge. Registration never dispatches, so nothing here is ever
 * called — but it MUST be passed by every test that builds the real server:
 * [RegisteredToolNames] is process-global and additive, so whichever test builds
 * first defines the ground-truth tool set. Omitting this in one call site makes
 * the browser_* tools invisible to the coverage tests depending on test order.
 */
internal object InertBrowserBridge : BrowserBridge {
    override fun isAvailable(mode: BrowserSessionMode): Boolean = error("not dispatched")
    override suspend fun open(url: String, mode: BrowserSessionMode, visible: Boolean): BrowserResult<BrowserPage> =
        error("not dispatched")
    override suspend fun read(mode: BrowserSessionMode): BrowserResult<BrowserPage> = error("not dispatched")
    override suspend fun act(
        mode: BrowserSessionMode,
        selector: String,
        action: BrowserAction,
        value: String?,
    ): BrowserResult<BrowserPage> = error("not dispatched")
    override suspend fun find(mode: BrowserSessionMode, text: String): BrowserResult<FindOutcome> =
        error("not dispatched")
    override suspend fun waitFor(
        mode: BrowserSessionMode,
        text: String?,
        timeoutMs: Long,
    ): BrowserResult<BrowserPage> = error("not dispatched")
    override suspend fun upload(
        mode: BrowserSessionMode,
        selector: String,
        fileUri: String,
    ): BrowserResult<BrowserPage> = error("not dispatched")
    override suspend fun extract(
        mode: BrowserSessionMode,
        fields: List<String>,
    ): BrowserResult<List<ExtractedRow>> = error("not dispatched")
    override suspend fun tabs(
        mode: BrowserSessionMode,
        action: com.luna.mcp.bridge.BrowserTabAction,
        url: String?,
        index: Int?,
    ): BrowserResult<com.luna.mcp.bridge.BrowserTabs> = error("not dispatched")
    override suspend fun handoff(
        mode: BrowserSessionMode,
        prompt: String,
    ): BrowserResult<com.luna.mcp.bridge.HandoffState> = error("not dispatched")
    override suspend fun handoffState(
        mode: BrowserSessionMode,
    ): BrowserResult<com.luna.mcp.bridge.HandoffState> = error("not dispatched")
    override suspend fun screenshot(mode: BrowserSessionMode): BrowserResult<BrowserCapture> =
        error("not dispatched")
    override suspend fun close(mode: BrowserSessionMode): BrowserResult<Unit> = error("not dispatched")
}
