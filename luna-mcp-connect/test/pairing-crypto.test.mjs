// Test vectors for the pairing handshake. The same values are asserted by the
// phone's PairingCryptoTest.kt: if one side changes, both tests must change.
import assert from "node:assert/strict";
import {
  extractFingerprint,
  binding,
  verificationCode,
  commitment,
  tokenHash,
  proofMac,
} from "../src/pairing-crypto.js";

const SDP =
  "v=0\r\no=- 1 2 IN IP4 127.0.0.1\r\n" +
  "a=fingerprint:sha-256 ab:CD:01:23:45:67:89:AB:CD:EF:01:23:45:67:89:AB:CD:EF:01:23:45:67:89:AB:CD:EF:01:23:45:67:89:AB\r\n" +
  "a=setup:actpass\r\n";
const TOKEN = "3f2c9b1e-7d4a-4c8e-9a1f-0b6d5e4c3a21";
const NONCE = "00112233445566778899aabbccddeeff";
const B = "luna-pair-v2|AA:BB|CC:DD";
const PHONE_NONCE = "ffeeddccbbaa99887766554433221100";

assert.equal(
  extractFingerprint(SDP),
  "AB:CD:01:23:45:67:89:AB:CD:EF:01:23:45:67:89:AB:CD:EF:01:23:45:67:89:AB:CD:EF:01:23:45:67:89:AB",
);
assert.equal(extractFingerprint("v=0\r\n"), null);
assert.equal(binding("AA:BB", "CC:DD"), B);
assert.equal(commitment(NONCE), "16b6224df8d644977c3fb1366b4751e8241275fc7a87657c8376de22eaa0fe65");
assert.equal(verificationCode(B, NONCE, PHONE_NONCE), "503243");
// The two nonces are not interchangeable.
assert.equal(verificationCode(B, PHONE_NONCE, NONCE), "227393");
assert.equal(tokenHash(TOKEN), "4f914ee5edc16944");
assert.equal(
  proofMac(TOKEN, B, NONCE),
  "b465a3a5c1eef9257f5769d6e4345c41c249073cff1311322358eac50197a123",
);
// A different binding (a man in the middle) must give a different proof.
assert.notEqual(proofMac(TOKEN, binding("AA:BB", "EE:FF"), NONCE), proofMac(TOKEN, B, NONCE));

console.log("pairing-crypto: all vectors match");
