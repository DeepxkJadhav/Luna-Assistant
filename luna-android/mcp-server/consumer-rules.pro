# Consumer ProGuard rules for the mcp-server module.
# Keep Ktor and MCP SDK reflection-using types.

-keep class io.ktor.** { *; }
-keep class io.modelcontextprotocol.kotlin.sdk.** { *; }
-keepclassmembers class kotlinx.serialization.json.** { *; }
-keep,includedescriptorclasses class com.luna.mcp.**$$serializer { *; }
-keepclassmembers class com.luna.mcp.** { *** Companion; }
-keepclasseswithmembers class com.luna.mcp.** { kotlinx.serialization.KSerializer serializer(...); }
