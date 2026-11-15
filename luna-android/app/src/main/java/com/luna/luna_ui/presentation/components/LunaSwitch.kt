package com.luna.luna_ui.presentation.components

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.luna.luna_ui.ui.theme.Mono

// ============================================================================
// Luna SWITCH — the ONE toggle used everywhere (settings, permissions, prefs).
//
// iOS-green checked track (Mono.ToggleGreen) is the single sanctioned color
// exception in the mono system: used ONLY as a switch's "on" fill, never as
// content, canvas, or accent. Defined once so no two toggles drift.
// ============================================================================

@Composable
fun LunaSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val scheme = rememberMonoScheme()
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        modifier = modifier,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = Mono.ToggleGreen,
            checkedBorderColor = Color.Transparent,
            uncheckedThumbColor = Color.White,
            uncheckedTrackColor = if (scheme.isDark) Mono.ToggleTrackOffDark else Mono.ToggleTrackOff,
            uncheckedBorderColor = Color.Transparent,
        ),
    )
}
