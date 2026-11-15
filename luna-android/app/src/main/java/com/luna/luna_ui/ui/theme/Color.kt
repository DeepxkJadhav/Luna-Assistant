package com.luna.luna_ui.ui.theme

import androidx.compose.ui.graphics.Color

// ========================================================================================
// Luna MONOCHROME DESIGN SYSTEM — PREMIUM BLACK & WHITE PALETTE
// Glassmorphism · Skeuomorphism · Pure Monochrome Elegance
// ========================================================================================

// === CORE BRAND IDENTITY — Pure Monochrome ===
val LunaPrimary = Color(0xFFFFFFFF)         // White — Primary brand
val LunaPrimaryVariant = Color(0xFFE5E5E5)  // Soft white
val LunaPrimaryLight = Color(0xFFFFFFFF)    // Pure white
val LunaPrimaryDark = Color(0xFFD4D4D4)     // Dim white

// === SECONDARY BRAND — Shades of Gray ===
val LunaSecondary = Color(0xFFA0A0A0)       // Mid gray — Sophistication
val LunaSecondaryVariant = Color(0xFF8A8A8A)
val LunaSecondaryLight = Color(0xFFBDBDBD)
val LunaSecondaryDark = Color(0xFF6E6E6E)

// === ACCENT PALETTE — Dark Tones ===
val LunaTertiary = Color(0xFF4A4A4A)        // Charcoal
val LunaTertiaryLight = Color(0xFF6E6E6E)
val LunaTertiaryDark = Color(0xFF2E2E2E)

val LunaAccent = Color(0xFFD4D4D4)          // Silver accent
val LunaAccentLight = Color(0xFFE8E8E8)
val LunaAccentDark = Color(0xFFB0B0B0)

// === PREMIUM NEUTRAL SYSTEM ===
val LunaNeutral950 = Color(0xFF000000)      // True black
val LunaNeutral900 = Color(0xFF0A0A0A)      // Near black
val LunaNeutral800 = Color(0xFF171717)      // Darkest surface
val LunaNeutral700 = Color(0xFF262626)      // Dark surface
val LunaNeutral600 = Color(0xFF404040)      // Secondary text dark
val LunaNeutral500 = Color(0xFF6B6B6B)      // Body text
val LunaNeutral400 = Color(0xFF8F8F8F)      // Placeholder text
val LunaNeutral300 = Color(0xFFB3B3B3)      // Disabled elements
val LunaNeutral200 = Color(0xFFD4D4D4)      // Subtle borders
val LunaNeutral100 = Color(0xFFEDEDED)      // Background secondary
val LunaNeutral50 = Color(0xFFF7F7F7)       // Background primary
val LunaWhite = Color(0xFFFFFFFF)            // Pure white

// === STATUS COLORS — Monochrome Luminance Scale ===
val LunaSuccess = Color(0xFFE0E0E0)         // Light gray — Success
val LunaSuccessLight = Color(0xFFF2F2F2)
val LunaSuccessDark = Color(0xFFBBBBBB)
val LunaSuccessContainer = Color(0xFFF7F7F7)

val LunaWarning = Color(0xFF8F8F8F)         // Medium gray — Warning
val LunaWarningLight = Color(0xFFD4D4D4)
val LunaWarningDark = Color(0xFF6B6B6B)
val LunaWarningContainer = Color(0xFFEDEDED)

val LunaError = Color(0xFF3A3A3A)           // Dark gray — Error
val LunaErrorLight = Color(0xFF6B6B6B)
val LunaErrorDark = Color(0xFF1A1A1A)
val LunaErrorContainer = Color(0xFF4A4A4A)

val LunaInfo = Color(0xFFC0C0C0)            // Silver — Info
val LunaInfoLight = Color(0xFFE0E0E0)
val LunaInfoDark = Color(0xFF9E9E9E)
val LunaInfoContainer = Color(0xFFF0F0F0)

// === AI ASSISTANT STATE COLORS — Monochrome ===
val LunaListening = Color(0xFFFFFFFF)       // White — Active recording
val LunaProcessing = Color(0xFFB0B0B0)      // Silver — AI thinking
val LunaResponding = Color(0xFFD4D4D4)      // Light gray — AI speaking
val LunaIdle = Color(0xFF6B6B6B)            // Dark gray — Waiting
val LunaConnecting = Color(0xFF8F8F8F)      // Medium gray — Connecting
val LunaError_State = Color(0xFF3A3A3A)     // Charcoal — Error state

// === MONOCHROME GRADIENT COLLECTIONS ===
object LunaGradients {
    val Primary = listOf(
        Color(0xFFFFFFFF),    // White
        Color(0xFFD4D4D4),    // Silver
    )

    val PrimarySubtle = listOf(
        Color(0x20FFFFFF),    // Ghost white
        Color(0x20D4D4D4),    // Ghost silver
    )

    val AI = listOf(
        Color(0xFFFFFFFF),    // White
        Color(0xFFD4D4D4),    // Silver
        Color(0xFFA0A0A0),    // Mid gray
    )

    val Success = listOf(
        Color(0xFFE0E0E0),
        Color(0xFFBBBBBB),
    )

    val Warning = listOf(
        Color(0xFF8F8F8F),
        Color(0xFF6B6B6B),
    )

    val Error = listOf(
        Color(0xFF3A3A3A),
        Color(0xFF1A1A1A),
    )

    // Glassmorphism gradients
    val Glass = listOf(
        Color(0x33FFFFFF),    // Frosted light
        Color(0x1AFFFFFF),    // Subtle frost
    )

    val GlassDark = listOf(
        Color(0x33000000),    // Dark frost
        Color(0x1A000000),    // Subtle dark frost
    )

    val GlassBorder = listOf(
        Color(0x40FFFFFF),    // Glass edge highlight
        Color(0x15FFFFFF),    // Glass edge fade
    )

    val Overlay = listOf(
        Color(0xB3000000),    // Heavy overlay
        Color(0x00000000),    // Transparent
    )

    // Skeuomorphic surface gradients (subtle 3D depth)
    val SurfaceRaised = listOf(
        Color(0xFFF7F7F7),    // Top highlight
        Color(0xFFE8E8E8),    // Bottom shadow
    )

    val SurfacePressed = listOf(
        Color(0xFFE0E0E0),    // Top shadow
        Color(0xFFEDEDED),    // Bottom highlight
    )

    val SurfaceDarkRaised = listOf(
        Color(0xFF2A2A2A),    // Top highlight
        Color(0xFF171717),    // Bottom shadow
    )
}

// === SURFACE SYSTEM ===
val LunaSurfacePrimary = Color(0xFFF7F7F7)
val LunaSurfaceSecondary = Color(0xFFFFFFFF)
val LunaSurfaceTertiary = Color(0xFFEDEDED)
val LunaSurfaceElevated = Color(0xFFFFFFFF)
val LunaSurfaceContainer = Color(0xFFF5F5F5)
val LunaSurfaceContainerHigh = Color(0xFFEDEDED)

// Dark theme surfaces
val LunaSurfacePrimaryDark = Color(0xFF000000)
val LunaSurfaceSecondaryDark = Color(0xFF0A0A0A)
val LunaSurfaceTertiaryDark = Color(0xFF171717)
val LunaSurfaceElevatedDark = Color(0xFF1A1A1A)

// === INTERACTIVE STATES ===
val LunaInteractive = LunaNeutral800
val LunaInteractiveHover = LunaNeutral700
val LunaInteractivePressed = LunaNeutral600
val LunaInteractiveDisabled = LunaNeutral300

val LunaFocus = LunaWhite
val LunaFocusRing = Color(0x4DFFFFFF)
val LunaSelection = Color(0x1AFFFFFF)

// === BORDER SYSTEM ===
val LunaBorder = LunaNeutral200
val LunaBorderHover = LunaNeutral300
val LunaBorderFocus = LunaWhite
val LunaBorderError = LunaError
val LunaBorderSuccess = LunaSuccess

val LunaBorderDark = LunaNeutral700
val LunaBorderHoverDark = LunaNeutral600
val LunaBorderFocusDark = LunaWhite

// === LEGACY COMPATIBILITY ===
val Purple80 = LunaSecondaryLight
val PurpleGrey80 = LunaNeutral400
val Pink80 = LunaAccentLight
val Purple40 = LunaSecondaryDark
val PurpleGrey40 = LunaNeutral600
val Pink40 = LunaAccentDark

val LunaGradientPrimary = LunaGradients.Primary
val LunaGradientSecondary = listOf(LunaSecondary, LunaSecondaryLight)
val LunaGradientAccent = listOf(LunaTertiary, LunaTertiaryLight)
val LunaGradientSuccess = LunaGradients.Success
val LunaGradientWarning = LunaGradients.Warning
val LunaGradientError = LunaGradients.Error
