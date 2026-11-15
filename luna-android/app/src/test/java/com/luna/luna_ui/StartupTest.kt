package com.luna.luna_ui

import android.content.Context
import com.luna.luna_ui.data.preferences.OnboardingPreferences
import com.luna.luna_ui.data.preferences.ThemeManager
import com.luna.luna_ui.remote.RemoteGateManager
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class StartupTest {

    @Test
    fun testStartupHydration() {
        val app: Context = RuntimeEnvironment.getApplication()
        assertNotNull(app)

        // Test LunaApplication onCreate components
        com.luna.luna_ui.data.preferences.DiagnosticsStore.hydrate(app)
        RemoteGateManager.hydrate(app)
        com.luna.luna_ui.audio.RecordableVoiceStore.hydrate(app)
        com.luna.luna_ui.data.preferences.ReadScreenImageStore.hydrate(app)

        ThemeManager.initialize(app)
        OnboardingPreferences.init(app)
    }
}
