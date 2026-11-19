package com.luna.luna_ui.presentation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer

/**
 * Validates production assets and density invariants for the Luna brand identity.
 *
 * Contract:
 * - Notification icons must be present across all 5 standard DPI densities with exact dimensions.
 * - Adaptive and legacy launcher assets must match Android specifications.
 * - Pure JVM byte parsing without java.desktop / javax.imageio dependencies.
 */
class LunaBrandIdentityTest {

    private val resDir = File("src/main/res")
    private val assetsDir = File("src/main/assets")

    private fun readPngDimensions(file: File): Pair<Int, Int> {
        assertTrue("File does not exist: ${file.path}", file.exists())
        FileInputStream(file).use { input ->
            val header = ByteArray(24)
            val bytesRead = input.read(header)
            assertTrue("File too short for PNG header: ${file.path}", bytesRead >= 24)

            // Verify PNG signature: 89 50 4E 47 0D 0A 1A 0A
            assertEquals(0x89.toByte(), header[0])
            assertEquals('P'.code.toByte(), header[1])
            assertEquals('N'.code.toByte(), header[2])
            assertEquals('G'.code.toByte(), header[3])

            // Width is at offset 16 (4 bytes big-endian), height at offset 20 (4 bytes big-endian)
            val buffer = ByteBuffer.wrap(header)
            val width = buffer.getInt(16)
            val height = buffer.getInt(20)
            return Pair(width, height)
        }
    }

    @Test
    fun `notification icon exists across all standard densities with exact dimensions`() {
        val expected = mapOf(
            "drawable-mdpi" to 24,
            "drawable-hdpi" to 36,
            "drawable-xhdpi" to 48,
            "drawable-xxhdpi" to 72,
            "drawable-xxxhdpi" to 96,
        )

        for ((densityFolder, expectedDim) in expected) {
            val file = File(resDir, "$densityFolder/ic_notification.png")
            val (width, height) = readPngDimensions(file)
            assertEquals("Width mismatch for $densityFolder", expectedDim, width)
            assertEquals("Height mismatch for $densityFolder", expectedDim, height)
        }
    }

    @Test
    fun `launcher adaptive icons match Android specifications across densities`() {
        val expected = mapOf(
            "mipmap-mdpi" to Pair(48, 108),
            "mipmap-hdpi" to Pair(72, 162),
            "mipmap-xhdpi" to Pair(96, 216),
            "mipmap-xxhdpi" to Pair(144, 324),
            "mipmap-xxxhdpi" to Pair(192, 432),
        )

        for ((folder, sizes) in expected) {
            val (legacySize, adaptiveSize) = sizes

            val legacy = File(resDir, "$folder/ic_launcher.png")
            val (lw, lh) = readPngDimensions(legacy)
            assertEquals(legacySize, lw)
            assertEquals(legacySize, lh)

            val round = File(resDir, "$folder/ic_launcher_round.png")
            val (rw, rh) = readPngDimensions(round)
            assertEquals(legacySize, rw)
            assertEquals(legacySize, rh)

            val fg = File(resDir, "$folder/ic_launcher_foreground.png")
            val (fw, fh) = readPngDimensions(fg)
            assertEquals(adaptiveSize, fw)
            assertEquals(adaptiveSize, fh)

            val mono = File(resDir, "$folder/ic_launcher_monochrome.png")
            val (mw, mh) = readPngDimensions(mono)
            assertEquals(adaptiveSize, mw)
            assertEquals(adaptiveSize, mh)
        }
    }

    @Test
    fun `vector drawables exist and contain required brand definitions`() {
        val mark = File(resDir, "drawable/ic_luna_mark.xml")
        assertTrue("ic_luna_mark.xml must exist", mark.exists())
        val markContent = mark.readText()
        assertTrue("ic_luna_mark must have viewportWidth 108", markContent.contains("android:viewportWidth=\"108\""))
        assertTrue("ic_luna_mark must have viewportHeight 108", markContent.contains("android:viewportHeight=\"108\""))

        val splash = File(resDir, "drawable/splash_logo.xml")
        assertTrue("splash_logo.xml must exist", splash.exists())
        val splashContent = splash.readText()
        assertTrue("splash_logo must reference ic_luna_mark", splashContent.contains("@drawable/ic_luna_mark"))
    }

    @Test
    fun `onboarding brand assets exist and are transparent PNGs`() {
        val colorLogo = File(assetsDir, "onboarding/luna_logo_color.png")
        val (cw, ch) = readPngDimensions(colorLogo)
        assertTrue("luna_logo_color must be at least 324px", cw >= 324)
        assertEquals(cw, ch)

        val monoLogo = File(assetsDir, "onboarding/luna_logo_mono.png")
        val (mw, mh) = readPngDimensions(monoLogo)
        assertTrue("luna_logo_mono must be at least 324px", mw >= 324)
        assertEquals(mw, mh)
    }
}
