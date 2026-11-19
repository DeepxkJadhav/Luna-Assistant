package com.luna.luna_ui.presentation.components

import android.graphics.RuntimeShader
import android.os.Build
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.luna.luna_ui.ui.theme.LunaBrandCyan
import com.luna.luna_ui.ui.theme.LunaBrandMagenta
import com.luna.luna_ui.ui.theme.LunaBrandPearl
import com.luna.luna_ui.ui.theme.LunaBrandStopsLooped
import com.luna.luna_ui.ui.theme.LunaBrandViolet

// ============================================================================
// LUNA SIGNATURE LOGO MARK — The Celestial Crescent & Starlight Companion.
//
// An original vector emblem designed specifically for Luna:
// a graceful, luminous crescent cradling a brilliant starlight pearl.
//
// In idle mode, it presents a crisp celestial starlight pearl.
// In active mode, it radiates an ethereal celestial aurora swirl with
// a breathing starlight halo.
// ============================================================================

/** AGSL celestial lavender illumination shader: subtle pearl and lavender flow. */
private const val LUNA_FLOW_AGSL = """
uniform float uTime;
uniform float2 uRes;
half3 pal(float x) {
    half3 c0 = half3(0.973, 0.980, 0.988); // Starlight Pearl White
    half3 c1 = half3(0.867, 0.839, 0.996); // Soft Lavender
    half3 c2 = half3(0.769, 0.710, 0.992); // Radiant Lilac
    half3 c3 = half3(0.650, 0.550, 0.980); // Subtle Lavender Illumination
    half3 c4 = half3(1.000, 1.000, 1.000); // Pure Pearl White
    x = fract(x) * 5.0;
    float i = floor(x);
    float f = smoothstep(0.0, 1.0, fract(x));
    half3 a; half3 b;
    if (i < 1.0)      { a = c0; b = c1; }
    else if (i < 2.0) { a = c1; b = c2; }
    else if (i < 3.0) { a = c2; b = c3; }
    else if (i < 4.0) { a = c3; b = c4; }
    else              { a = c4; b = c0; }
    return mix(a, b, half(f));
}
half4 main(float2 fragCoord) {
    float2 uv = (fragCoord - 0.5 * uRes) / uRes.y;
    float t = uTime * 0.08;
    float2 p = uv * 1.6;
    p += 0.25 * float2(sin(p.y * 2.4 + t * 1.7), cos(p.x * 2.1 - t * 1.3));
    float a = t * 0.6 + sin(t * 0.5) * 0.3;
    float ca = cos(a); float sa = sin(a);
    p = float2(ca * p.x - sa * p.y, sa * p.x + ca * p.y);
    float ang = atan(p.y, p.x);
    float rad = length(p);
    float g = ang / 6.2831853 + t * 0.5 + 0.35 * sin(rad * 3.0 - t * 2.0);
    half3 col = pal(g);
    col *= half(0.85 + 0.25 * (1.0 - rad));
    return half4(col, 1.0);
}
"""

/**
 * The Luna logo as the primary input-bar button.
 *
 * @param active when true the mark fills with the flowing brand gradient (thinking/speaking);
 *   when false it shows a clean white logo (idle). Transitions cross-fade smoothly.
 */
@Composable
fun LunaLogoButton(
    active: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    contentDescription: String = "Luna",
) {
    val activeAlpha by animateFloatAsState(
        targetValue = if (active) 1f else 0f,
        animationSpec = tween(durationMillis = 650),
        label = "logoActive",
    )

    var time by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(active) {
        if (!active) return@LaunchedEffect
        val t0 = System.nanoTime()
        while (true) {
            withInfiniteAnimationFrameMillis {
                time = ((System.nanoTime() - t0) / 1_000_000_000f)
            }
        }
    }

    val shader = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            runCatching { RuntimeShader(LUNA_FLOW_AGSL) }.getOrNull()
        } else {
            null
        }
    }

    Box(
        modifier = modifier
            .size(size)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1E1B4B).copy(alpha = 0.85f),
                        Color(0xFF0F172A),
                    ),
                ),
                shape = CircleShape,
            )
            .semantics { this.contentDescription = contentDescription },
    ) {
        // Soft celestial bloom behind the mark while active.
        if (activeAlpha > 0f) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val r = this.size.minDimension * 0.72f
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            LunaBrandViolet.copy(alpha = 0.38f * activeAlpha),
                            LunaBrandCyan.copy(alpha = 0.20f * activeAlpha),
                            Color.Transparent,
                        ),
                        center = center,
                        radius = r,
                    ),
                    radius = r,
                    center = center,
                )
            }
        }

        // Idle: crisp starlight pearl vector.
        if (activeAlpha < 1f) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = 1f - activeAlpha },
            ) {
                drawLunaVectorLogo(brush = Brush.linearGradient(listOf(Color.White, LunaBrandPearl)))
            }
        }

        // Active: flowing celestial aurora gradient clipped to the Luna emblem.
        if (activeAlpha > 0f) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        this.alpha = activeAlpha
                        compositingStrategy = CompositingStrategy.Offscreen
                    },
            ) {
                if (shader != null) {
                    shader.setFloatUniform("uTime", time)
                    shader.setFloatUniform("uRes", this.size.width, this.size.height)
                    drawRect(brush = ShaderBrush(shader))
                } else {
                    drawRect(brush = Brush.linearGradient(LunaBrandStopsLooped))
                }
                drawLightSweep(time)
                // Mask everything above to the Luna emblem shape.
                drawLunaVectorLogo(
                    brush = Brush.linearGradient(listOf(Color.White, Color.White)),
                    blendMode = BlendMode.DstIn,
                )
            }
        }
    }
}

/** Static colored logo mark for AI reply avatars. */
@Composable
fun LunaLogoAvatar(
    modifier: Modifier = Modifier,
    size: Dp = 28.dp,
) {
    Canvas(modifier = modifier.size(size)) {
        drawLunaVectorLogo(
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFFEDE9FE), Color(0xFFC4B5FD)),
            ),
        )
    }
}

/** Static white Luna mark for dark branded controls such as the Browser pill. */
@Composable
fun LunaWhiteLogo(
    modifier: Modifier = Modifier,
    size: Dp = 22.dp,
) {
    Canvas(modifier = modifier.size(size)) {
        drawLunaVectorLogo(brush = Brush.linearGradient(listOf(Color.White, Color.White)))
    }
}

/**
 * Pure vector rendering of the Luna Celestial Crescent & Starlight Companion.
 * Perfectly sharp and scalable at any resolution.
 */
fun DrawScope.drawLunaVectorLogo(
    brush: Brush,
    alpha: Float = 1f,
    blendMode: BlendMode = BlendMode.SrcOver,
) {
    val center = this.center
    val radius = size.minDimension * 0.40f

    // 1. The Luminous Crescent Moon with subtle notch
    val moonPath = Path().apply {
        addArc(
            oval = Rect(center = Offset(center.x - radius * 0.08f, center.y), radius = radius),
            startAngleDegrees = -118f,
            sweepAngleDegrees = 236f,
        )
        val innerCenter = Offset(center.x + radius * 0.28f, center.y)
        arcTo(
            rect = Rect(center = innerCenter, radius = radius * 0.78f),
            startAngleDegrees = 118f,
            sweepAngleDegrees = -236f,
            forceMoveTo = false,
        )
        close()
    }
    drawPath(path = moonPath, brush = brush, alpha = alpha, blendMode = blendMode)

    // 2. The Companion Four-Pointed Starlight Star within the crescent's embrace
    val starCenter = Offset(center.x + radius * 0.32f, center.y - radius * 0.04f)
    val starR = radius * 0.24f
    val starPath = Path().apply {
        val top = Offset(starCenter.x, starCenter.y - starR)
        val right = Offset(starCenter.x + starR * 0.70f, starCenter.y)
        val bottom = Offset(starCenter.x, starCenter.y + starR)
        val left = Offset(starCenter.x - starR * 0.70f, starCenter.y)
        val waist = starR * 0.10f

        moveTo(top.x, top.y)
        quadraticTo(starCenter.x + waist, starCenter.y - waist, right.x, right.y)
        quadraticTo(starCenter.x + waist, starCenter.y + waist, bottom.x, bottom.y)
        quadraticTo(starCenter.x - waist, starCenter.y + waist, left.x, left.y)
        quadraticTo(starCenter.x - waist, starCenter.y - waist, top.x, top.y)
        close()
    }
    drawPath(path = starPath, brush = brush, alpha = alpha, blendMode = blendMode)

    // 3. Central Starlight Core
    drawCircle(
        brush = brush,
        radius = starR * 0.22f,
        center = starCenter,
        alpha = alpha,
        blendMode = blendMode,
    )
}

/** A single subtle starlight highlight sweeping across the emblem. */
private fun DrawScope.drawLightSweep(timeSec: Float) {
    val period = 5.0f
    val phase = (timeSec % period) / period
    val eased = if (phase < 0.70f) -0.3f else (phase - 0.70f) / 0.30f * 1.6f - 0.3f
    val sx = eased * this.size.width
    val bandW = this.size.width * 0.18f
    drawRect(
        brush = Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.White.copy(alpha = 0.50f), Color.Transparent),
            start = Offset(sx - bandW, 0f),
            end = Offset(sx + bandW, this.size.height),
        ),
    )
}
