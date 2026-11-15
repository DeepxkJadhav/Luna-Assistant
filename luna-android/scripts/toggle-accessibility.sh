#!/usr/bin/env bash
# Dev-only toggle for Luna's accessibility service, to test GPay/UPI apps
# without racing their own "accessibility service enabled" check.
#
# Usage:
#   ./toggle-accessibility.sh off   # before opening GPay
#   ./toggle-accessibility.sh on    # after you're done testing
#
# Root cause: GPay checks AccessibilityManager state in its own onResume(),
# which can win the race against Luna's reactive disableSelf() (fired from a
# TYPE_WINDOW_STATE_CHANGED event that arrives around the same time). This
# flips the OS-level master switch directly — no race, works every time.

set -euo pipefail

SERVICE="com.luna.luna_ui.feature.debug/com.luna.luna_ui.accessibility.LunaAccessibilityService"

case "${1:-}" in
  off)
    adb shell settings put secure accessibility_enabled 0
    echo "Accessibility OFF — GPay/UPI apps should work normally now."
    ;;
  on)
    adb shell settings put secure enabled_accessibility_services "$SERVICE"
    adb shell settings put secure accessibility_enabled 1
    echo "Accessibility ON — Luna is back."
    ;;
  *)
    echo "Usage: $0 {off|on}" >&2
    exit 1
    ;;
esac
