package com.luna.luna_ui.data.repository

import com.luna.luna_ui.domain.model.VoiceSessionState
import kotlinx.coroutines.flow.StateFlow

/**
 * Repository interface for assistant operations
 */
interface AssistantRepository {
    val voiceSessionState: StateFlow<VoiceSessionState>

    suspend fun startListening()

    suspend fun stopListening()

    suspend fun processTextCommand(text: String)

    suspend fun initializeSession()

    suspend fun cleanup()
}
