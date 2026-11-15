package com.luna.luna_ui.services

import com.luna.mcp.McpServerHealth
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * This decision used to live as a private method on a Service, so nothing could
 * test it — which is precisely why it shipped mapping a *starting* MCP server to
 * the verb "Stuck". A booting server is not the agent being stuck.
 *
 * The rule under test: [LunaStatus] means **activity**, never health.
 */
class ChipStatusPolicyTest {

    private val listening = McpServerHealth.Listening(
        port = 4816,
        host = "127.0.0.1",
        reachableAddresses = emptyList(),
    )

    // ── health never becomes an activity verb ──────────────────────────

    @Test
    fun `a stopped server reports no activity rather than an error verb`() {
        assertEquals(
            LunaStatus.Idle,
            ChipStatusPolicy.decide(McpServerHealth.Stopped, LunaStatus.Acting, LunaStatus.Idle),
        )
    }

    @Test
    fun `a starting server is not reported as an activity`() {
        // The original defect: "Starting" surfaced to the user as "Stuck".
        assertEquals(
            LunaStatus.Idle,
            ChipStatusPolicy.decide(McpServerHealth.Starting, LunaStatus.Thinking, LunaStatus.Idle),
        )
    }

    @Test
    fun `a crashed server reports no activity rather than an error verb`() {
        assertEquals(
            LunaStatus.Idle,
            ChipStatusPolicy.decide(
                McpServerHealth.Crashed(reason = "port in use"),
                LunaStatus.Acting,
                LunaStatus.Idle,
            ),
        )
    }

    @Test
    fun `tool activity is ignored when the server cannot dispatch tools`() {
        // Stale tool status must not survive the server going down — no tool can
        // be running if the server isn't listening.
        assertEquals(
            LunaStatus.Idle,
            ChipStatusPolicy.decide(McpServerHealth.Stopped, LunaStatus.Acting, LunaStatus.Idle),
        )
    }

    @Test
    fun `voice still shows while the server is down - it runs on-device`() {
        assertEquals(
            LunaStatus.Listening,
            ChipStatusPolicy.decide(McpServerHealth.Stopped, LunaStatus.Idle, LunaStatus.Listening),
        )
    }

    // ── priority between the two live sources ──────────────────────────

    @Test
    fun `tool activity shows when the server is listening and voice is idle`() {
        assertEquals(
            LunaStatus.Acting,
            ChipStatusPolicy.decide(listening, LunaStatus.Acting, LunaStatus.Idle),
        )
    }

    @Test
    fun `voice wins over tool activity on a tie`() {
        assertEquals(
            LunaStatus.Speaking,
            ChipStatusPolicy.decide(listening, LunaStatus.Acting, LunaStatus.Speaking),
        )
    }

    @Test
    fun `everything idle stays idle`() {
        assertEquals(
            LunaStatus.Idle,
            ChipStatusPolicy.decide(listening, LunaStatus.Idle, LunaStatus.Idle),
        )
    }

    // ── control lock (spec 2026-07-31) ─────────────────────────────────

    @Test
    fun `a paused agent shows nothing at all`() {
        // The spec is explicit: when pause engages the status pill HIDES. The agent is
        // not doing anything, so a "doing" chip would be a lie — and the Pause pill owns
        // the screen at that moment instead. Two pills at once is the confusion the
        // separate-components split exists to prevent.
        assertEquals(
            LunaStatus.Idle,
            ChipStatusPolicy.decide(
                health = listening,
                toolStatus = LunaStatus.Acting,
                voiceStatus = LunaStatus.Idle,
                pausedByHuman = true,
            ),
        )
    }

    @Test
    fun `a paused agent hides voice status too`() {
        // Voice runs on-device and is otherwise unaffected by anything — but while the
        // human holds the wheel, nothing Luna does should be announced.
        assertEquals(
            LunaStatus.Idle,
            ChipStatusPolicy.decide(
                health = listening,
                toolStatus = LunaStatus.Idle,
                voiceStatus = LunaStatus.Listening,
                pausedByHuman = true,
            ),
        )
    }

    @Test
    fun `resuming brings the status pill straight back`() {
        assertEquals(
            LunaStatus.Acting,
            ChipStatusPolicy.decide(
                health = listening,
                toolStatus = LunaStatus.Acting,
                voiceStatus = LunaStatus.Idle,
                pausedByHuman = false,
            ),
        )
    }
}
