package com.luna.luna_ui.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.luna.luna_ui.presentation.components.rememberMonoScheme

// ============================================================================
// LUNA CELESTIAL IDENTITY — App-wide canvas & Home hero styling.
//
// A tranquil, intelligent celestial environment tailored for Luna:
// deep velvet twilight, starlight silver, luminous iris, and warm solar amber
// attention cues.
// ============================================================================

data class WarmScheme(
    val isDark: Boolean,
    // page canvas (subtle, used app-wide by the scaffold)
    val canvasTop: Color,
    val canvasBottom: Color,
    // rich Home background
    val homeTop: Color,
    val homeMid: Color,
    val homeBottom: Color,
    // primary "hero" card (serene celestial gradient)
    val heroA: Color,
    val heroB: Color,
    val onHero: Color,
    val onHeroDim: Color,
    // frosted secondary cards
    val card: Color,
    val cardBorder: Color,
    // text on canvas
    val textPrimary: Color,
    val textSecondary: Color,
    // circular icon buttons on canvas
    val iconButton: Color,
    val iconButtonContent: Color,
    // attention/status accent (warm starlight amber)
    val blood: Color,
)

private val WarmLight = WarmScheme(
    isDark = false,
    canvasTop = Color(0xFFF8FAFC),
    canvasBottom = Color(0xFFF1F5F9),
    homeTop = Color(0xFFF5F3FF),
    homeMid = Color(0xFFEFF6FF),
    homeBottom = Color(0xFFE0F2FE),
    heroA = Color(0xFF6366F1),
    heroB = Color(0xFF4F46E5),
    onHero = Color(0xFFFFFFFF),
    onHeroDim = Color(0xFFE0E7FF),
    card = Color(0xEEFFFFFF),
    cardBorder = Color(0x266366F1),
    textPrimary = Color(0xFF0F172A),
    textSecondary = Color(0xFF475569),
    iconButton = Color(0xF2FFFFFF),
    iconButtonContent = Color(0xFF0F172A),
    blood = Color(0xFFF59E0B),
)

private val WarmDark = WarmScheme(
    isDark = true,
    canvasTop = Color(0xFF090D16),
    canvasBottom = Color(0xFF05080E),
    homeTop = Color(0xFF0F172A),
    homeMid = Color(0xFF1E1B4B),
    homeBottom = Color(0xFF172554),
    heroA = Color(0xFF4338CA),
    heroB = Color(0xFF312E81),
    onHero = Color(0xFFF8FAFC),
    onHeroDim = Color(0xFFC7D2FE),
    card = Color(0xCC1E293B),
    cardBorder = Color(0x33818CF8),
    textPrimary = Color(0xFFF8FAFC),
    textSecondary = Color(0xFF94A3B8),
    iconButton = Color(0x26FFFFFF),
    iconButtonContent = Color(0xFFF8FAFC),
    blood = Color(0xFFFBBF24),
)

@Composable
fun rememberWarmScheme(): WarmScheme = if (rememberMonoScheme().isDark) WarmDark else WarmLight

/** Subtle celestial canvas gradient used behind every screen (via LunaScreenScaffold). */
fun WarmScheme.canvasBrush(): Brush = Brush.verticalGradient(listOf(canvasTop, canvasBottom))

/** The rich Home background gradient (soft twilight to cosmic morning mist). */
fun WarmScheme.homeBrush(): Brush = Brush.verticalGradient(listOf(homeTop, homeMid, homeBottom))

/** The bold primary-card gradient (luminous iris to deep twilight). */
fun WarmScheme.heroBrush(): Brush = Brush.linearGradient(listOf(heroA, heroB))
