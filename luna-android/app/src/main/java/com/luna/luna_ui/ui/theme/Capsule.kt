package com.luna.luna_ui.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.luna.luna_ui.presentation.components.rememberMonoScheme

// ============================================================================
// CAPSULE — Luna's Celestial Design System.
//
// Luminous surfaces, midnight obsidian cards, celestial starlight accents,
// and the velvet capsule strip as the app's primary navigation control.
// ============================================================================

data class CapsuleScheme(
    val isDark: Boolean,
    // page
    val canvas: Color,          // celestial field everything sits on
    val card: Color,            // luminous sheet cards
    val cardSoft: Color,        // recessed chip/icon wells inside cards
    val line: Color,            // hairline dividers/borders on canvas + cards
    // charcoal ("the dark card world")
    val char: Color,            // charcoal card top
    val charDeep: Color,        // charcoal card bottom
    val onChar: Color,          // primary text on charcoal
    val onCharDim: Color,       // secondary text on charcoal
    val charLine: Color,        // hairlines inside charcoal
    // text on canvas/cards
    val ink: Color,
    val sub: Color,
    // celestial accent
    val accent: Color,
    val accentSoft: Color,      // soft field behind accent icons/badges
    val onAccent: Color,
    // hero gradient (celestial starlight field inside the hero card)
    val heroCore: Color,
    val heroMid: Color,
    val heroEdge: Color,
    // capsule nav strip
    val capsule: Color,
    val onCapsule: Color,
    val onCapsuleDim: Color,
    // status
    val ok: Color,
)

private val CapsuleLight = CapsuleScheme(
    isDark = false,
    canvas = Color(0xFFF8FAFC),
    card = Color(0xFFFFFFFF),
    cardSoft = Color(0xFFF1F5F9),
    line = Color(0xFFE2E8F0),
    char = Color(0xFF1E1B4B),
    charDeep = Color(0xFF0F172A),
    onChar = Color(0xFFF8FAFC),
    onCharDim = Color(0xFF94A3B8),
    charLine = Color(0x1AFFFFFF),
    ink = Color(0xFF0F172A),
    sub = Color(0xFF64748B),
    accent = Color(0xFF6366F1),
    accentSoft = Color(0xFFEEF2FF),
    onAccent = Color(0xFFFFFFFF),
    heroCore = Color(0xFFE0E7FF),
    heroMid = Color(0xFFEDE9FE),
    heroEdge = Color(0xFFF8FAFC),
    capsule = Color(0xFF0F172A),
    onCapsule = Color(0xFFF8FAFC),
    onCapsuleDim = Color(0xFF94A3B8),
    ok = Color(0xFF10B981),
)

private val CapsuleDark = CapsuleScheme(
    isDark = true,
    canvas = Color(0xFF090D16),
    card = Color(0xFF111827),
    cardSoft = Color(0xFF1E293B),
    line = Color(0xFF1E293B),
    char = Color(0xFF1E1B4B),
    charDeep = Color(0xFF0F172A),
    onChar = Color(0xFFF8FAFC),
    onCharDim = Color(0xFF94A3B8),
    charLine = Color(0x1AFFFFFF),
    ink = Color(0xFFF8FAFC),
    sub = Color(0xFF94A3B8),
    accent = Color(0xFF818CF8),
    accentSoft = Color(0xFF312E81),
    onAccent = Color(0xFFFFFFFF),
    heroCore = Color(0xFF312E81),
    heroMid = Color(0xFF1E1B4B),
    heroEdge = Color(0xFF090D16),
    capsule = Color(0xFF030712),
    onCapsule = Color(0xFFF8FAFC),
    onCapsuleDim = Color(0xFF94A3B8),
    ok = Color(0xFF34D399),
)

object Capsule {
    /** Smooth, soft organic radii embodying Luna's caring nature. */
    val ShapeHero = RoundedCornerShape(26.dp)
    val ShapeCard = RoundedCornerShape(22.dp)
    val ShapeChipWell = RoundedCornerShape(12.dp)
    val ShapePill = RoundedCornerShape(percent = 50)

    @Composable
    fun scheme(): CapsuleScheme = if (rememberMonoScheme().isDark) CapsuleDark else CapsuleLight
}

/** The luminous starlight hero field (celestial core fading softly to canvas). */
fun CapsuleScheme.heroBrush(): Brush = Brush.radialGradient(
    colors = listOf(heroCore, heroMid, heroEdge),
)

/** Charcoal card field (deep velvet twilight with top light). */
fun CapsuleScheme.charBrush(): Brush = Brush.verticalGradient(listOf(char, charDeep))
