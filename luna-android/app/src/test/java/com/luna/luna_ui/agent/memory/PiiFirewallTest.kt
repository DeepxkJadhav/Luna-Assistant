package com.luna.luna_ui.agent.memory

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PiiFirewallTest {
    @Test fun `redacts email`() {
        assertTrue(PiiFirewall.scrub("mail me at alex@gmail.com now").contains("<email>"))
        assertFalse(PiiFirewall.scrub("alex@gmail.com").contains("@gmail"))
    }

    @Test fun `redacts 10-digit phone`() {
        assertTrue(PiiFirewall.scrub("call 9876543210 please").contains("<number>"))
    }

    @Test fun `redacts handle and amount`() {
        assertTrue(PiiFirewall.scrub("ping @alexmorgan").contains("<handle>"))
        assertTrue(PiiFirewall.scrub("send $50 to bob").contains("<amount>"))
    }
}
