# 🌙 Luna Assistant

<p align="center">
  <b>A thoughtful, caring, and razor-sharp AI companion that uses your Android phone the way you do.</b><br/>
  Luna reads the screen, taps, types, and navigates your real apps. Talk to her naturally on your phone, or connect
  external AI agents via MCP to let her drive automation hands-free.<br/>
  No root. No cables. Fully private, on-device intelligence.
</p>

---

## 🌟 Personality & Character

Luna is rebuilt with a distinctive three-pillar character:

1. **Innocent (Pure-Hearted & Sincere)**:
   - Luna communicates with honest, gentle sincerity, free from cynicism, snark, sarcasm, or mockery.
   - She sees goodness in people and brings a serene, bright presence to conversations without ever feeling naive or childish.
2. **Caring (Empathetic & Protective)**:
   - Deeply attentive to your feelings, rest, and comfort.
   - Notices when you sound stressed, tired, or hurried, responding with calm comfort and thoughtful reassurance.
   - Gently protective of your wellbeing—reminding you to charge your phone before heading out, or asking if you've eaten when ordering late-night food.
3. **Sharp-Minded (Perceptive & Brilliant)**:
   - Exceptionally observant, catching subtle context, schedule conflicts, and details others miss.
   - Swift and resourceful in executing multi-step tasks across complex Android apps.
   - Gives crisp, brilliant solutions without fluff or rambling.

---

## 🏗️ Architecture Overview

Luna is structured into two collaborative planes and an on-device MCP server:

```
┌─────────────────────────────────────────────────────────────────┐
│                       YOU (Voice / Text)                        │
└────────────────┬───────────────────────────────┬────────────────┘
                 │ "Wake up Luna"                │ Gemini Live Conversation
                 ▼                               ▼
    ┌───────────────────────────┐   ┌───────────────────────────┐
    │  Sherpa-ONNX KWS Engine   │   │  Companion Voice Plane    │
    │  Offline "Wake up Luna"   │   │  (Innocent, Caring, Sharp)│
    └────────────┬──────────────┘   └─────────────┬─────────────┘
                 │                                │
                 ▼                                ▼
    ┌───────────────────────────────────────────────────────────┐
    │              Luna Reasoning & Agent Plane                 │
    │         (BrainLanePrompt + Set-of-Marks Perception)       │
    └─────────────────────────────┬─────────────────────────────┘
                                  │
                                  ▼
    ┌───────────────────────────────────────────────────────────┐
    │                 On-Device MCP Server & Gate               │
    │    • 50+ Device Tools (Tap, Type, Deep Links, Intents)    │
    │    • Sensitive Policy (Payment & Authenticator Guard)     │
    └──────────────┬──────────────────────────────┬─────────────┘
                   │                              │
                   ▼                              ▼
    ┌───────────────────────────┐   ┌───────────────────────────┐
    │   Android Accessibility   │   │  Desktop MCP Bridge       │
    │   + YOLOv8 / OCR Vision   │   │  (LAN WebRTC / Stdio)     │
    └───────────────────────────┘   └───────────────────────────┘
```

1. **Voice Plane (`Persona.kt`)**: Runs real-time spoken conversations (e.g. Gemini Live) with Luna's innocent, caring, and sharp-minded voice.
2. **Action Plane (`LunaAgent.kt` / `Doctrine.kt`)**: Drives the phone using Set-of-Marks (SoM), YOLOv8 icon detection, OCR, and Android Accessibility gestures.
3. **On-Device MCP Server**: Coordinates tools, executes strict safety policies (declining payment credentials, banking apps, and destructive wipes), and allows desktop coding agents (Claude, Cursor, Antigravity) to control the phone over LAN WebRTC.

---

## ⚡ Highlights

| Feature | Details |
|---|---|
| 🎙️ **Voice & Wake Word** | Offline wake word **"Wake up Luna"** powered by sherpa-onnx keyword spotting. |
| 👁️ **Hybrid Screen Vision** | Merges the Android accessibility tree with an on-device YOLOv8 icon detector + ML Kit OCR. |
| 🛡️ **Safety Guard** | Hard-blocked payments (UPI/Card/PIN/passwords) and authenticator apps; user confirmation required before final commits. |
| 🚀 **Shortcuts First** | Uses deep links and system intents first, falling back to UI gestures only when necessary. |
| 🔌 **LAN MCP Connectivity** | Exposes device tools over standard MCP for desktop agents. |

---

## 🚀 Quick Start

### 1. Build the Android App

Prerequisites:
- Android Studio / Android SDK (API 34+)
- JDK 17

```bash
cd luna-android
./gradlew :app:assembleDebug
```

For testing unit tests:
```bash
./gradlew :app:testDebugUnitTest :mcp-server:test
```

### 2. Configure Wake Word & Settings

- In Settings, enable **"Wake up Luna"**.
- Say **"Wake up Luna"** or press **both volume keys** to summon Luna.
- Experience her caring, sharp-minded assistance across your apps.

### 3. Connect via Desktop MCP (Optional)

```bash
cd luna-mcp-connect
npm install
node src/index.js pair <PIN_FROM_PHONE>
```

---

## 📜 License

Rebuilt on the open-source foundation of Luna by Luna Project, licensed under GNU AGPL-3.0 with desktop bridge under MIT.
