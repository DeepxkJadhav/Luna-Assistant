package com.luna.luna_ui.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// ========================================================================================
// LUNA CELESTIAL BRAND GRADIENT — The luminous moonlit palette
// Designed for Luna: serene, innocent, caring, and intellectually sharp.
// Soft moonlight silver, celestial cyan, starlight iris, and warm solar amber.
// ========================================================================================

val LunaBrandViolet = Color(0xFF6366F1)  // Luminous Celestial Iris
val LunaBrandCyan = Color(0xFF38BDF8)    // Ethereal Moonlight Sky
val LunaBrandPearl = Color(0xFFF8FAFC)   // Starlight Pearl
val LunaBrandMagenta = Color(0xFFA855F7) // Cosmic Nebula Purple
val LunaBrandAmber = Color(0xFFF59E0B)   // Warm Solar Glow / Attention
val LunaBrandOrange = Color(0xFFF59E0B)  // Replaced blood red with warm caring amber
val LunaBrandLime = Color(0xFF34D399)    // Aurora Mint Green
val LunaBrandEmerald = Color(0xFF10B981) // Serene Ready Green

/** The brand stops in harmonious celestial order. */
val LunaBrandStops = listOf(
    LunaBrandViolet,
    LunaBrandCyan,
    LunaBrandMagenta,
    LunaBrandLime,
    LunaBrandCyan,
)

/** Looped stops for a seamless celestial sweep gradient. */
val LunaBrandStopsLooped = LunaBrandStops + LunaBrandViolet

/** Diagonal brand gradient for filled accents (luminous celestial sweep). */
fun lunaBrandLinear(): Brush = Brush.linearGradient(
    colors = listOf(LunaBrandViolet, LunaBrandCyan, LunaBrandMagenta, LunaBrandCyan),
)

/** Two-stop celestial gradient for compact accents (mic idle/press ring, chips). */
fun lunaBrandDuo(): Brush = Brush.linearGradient(listOf(LunaBrandViolet, LunaBrandCyan))

/** Warm solar starlight gradient for alerts and highlights. */
fun lunaAmberGradient(): Brush = Brush.linearGradient(listOf(Color(0xFFFBBF24), Color(0xFFF59E0B)))
