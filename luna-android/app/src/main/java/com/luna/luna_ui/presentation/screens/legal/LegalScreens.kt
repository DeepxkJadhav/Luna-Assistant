package com.luna.luna_ui.presentation.screens.legal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.luna.luna_ui.presentation.components.LunaScreenScaffold
import com.luna.luna_ui.presentation.components.MonoCardGroup
import com.luna.luna_ui.presentation.components.MonoDivider
import com.luna.luna_ui.presentation.components.rememberMonoScheme

// ============================================================================
// LEGAL SCREENS — Privacy Policy, Terms of Service, Open-source licenses.
// All on the shared LunaScreenScaffold so they match the rest of the app.
// ============================================================================

@Composable
fun PrivacyPolicyScreen(onNavigateBack: () -> Unit, modifier: Modifier = Modifier) {
    LegalTextScreen("Privacy Policy", LegalCopy.PRIVACY, onNavigateBack, modifier)
}

@Composable
fun TermsScreen(onNavigateBack: () -> Unit, modifier: Modifier = Modifier) {
    LegalTextScreen("Terms of Service", LegalCopy.TERMS, onNavigateBack, modifier)
}

@Composable
private fun LegalTextScreen(
    title: String,
    body: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scheme = rememberMonoScheme()
    LunaScreenScaffold(title = title, onBack = onNavigateBack, modifier = modifier) {
        Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium,
            color = scheme.textPrimary,
        )
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun LicensesScreen(onNavigateBack: () -> Unit, modifier: Modifier = Modifier) {
    val scheme = rememberMonoScheme()
    LunaScreenScaffold(
        title = "Open-source licenses",
        subtitle = "Luna is built on these projects",
        onBack = onNavigateBack,
        modifier = modifier,
    ) {
        MonoCardGroup(scheme) {
            LegalCopy.LICENSES.forEachIndexed { index, (name, license) ->
                if (index > 0) MonoDivider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                        color = scheme.textPrimary,
                        modifier = Modifier.weight(1f),
                    )
                    Text(
                        text = license,
                        style = MaterialTheme.typography.bodySmall,
                        color = scheme.textSecondary,
                    )
                }
            }
        }
        Spacer(Modifier.height(32.dp))
    }
}
