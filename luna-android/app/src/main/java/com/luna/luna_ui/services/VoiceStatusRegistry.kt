package com.luna.luna_ui.services

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Process-global bridge that lets [com.luna.luna_ui.overlay.LunaOverlayService]
 * publish voice phase changes (Listening / Thinking / Speaking) without posting
 * its own promoted-chip notification.
 *
 * [AssistantForegroundService] reads [status] inside its 3-way combine() collector
 * and re-posts the single authoritative chip via startForeground(). This avoids the
 * "two foreground services fighting over the status bar" problem — only
 * AssistantForegroundService (id=7001) ever calls startForeground for the chip.
 */
object VoiceStatusRegistry {
    private val _status = MutableStateFlow<LunaStatus>(LunaStatus.Idle)
    val status: StateFlow<LunaStatus> = _status.asStateFlow()

    fun set(next: LunaStatus) {
        if (_status.value == next) return
        _status.value = next
    }
}
