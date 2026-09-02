package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Sleek Interface Backgrounds (Ultra Pitch Black #050505 and Nav/Bar #0A0A0A)
val SleekBackground = Color(0xFF050505)
val SleekSurface = Color(0xFF0A0A0A)
val SleekSurfaceVariant = Color(0xFF121212)
val SleekCardSurface = Color(0x0DFFFFFF) // White 5% (bg-white/5)
val SleekCardSurfaceHover = Color(0x1AFFFFFF) // White 10% (bg-white/10)
val SleekCardBorder = Color(0x1AFFFFFF) // White 10% border (border-white/10)
val SleekCardBorderLight = Color(0x26FFFFFF) // White 15% border
val SleekFlagBackground = Color(0xFF1E293B) // slate-800

// Sleek Interface Accents (Electric Cobalt / Royal Blue #2563EB / #3B82F6)
val SleekBluePrimary = Color(0xFF3B82F6) // Blue 500
val SleekBlueDark = Color(0xFF2563EB) // Blue 600
val SleekBlueDeep = Color(0xFF1D4ED8) // Blue 700
val SleekBlueGlow = Color(0x662563EB) // rgba(37,99,235,0.4)
val SleekBlueRingOuter = Color(0x0D2563EB) // bg-blue-600/5
val SleekBlueRingMiddle = Color(0x1A2563EB) // bg-blue-600/10
val SleekBlueBorderOuter = Color(0x1A3B82F6) // border-blue-500/10
val SleekBlueBorderMiddle = Color(0x333B82F6) // border-blue-500/20

// State Accents
val SleekEmerald = Color(0xFF10B981) // Emerald 500
val SleekEmeraldDark = Color(0xFF059669) // Emerald 600
val SleekEmeraldDeep = Color(0xFF047857) // Emerald 700
val SleekEmeraldGlow = Color(0x6610B981) // rgba(16,185,129,0.4)
val SleekAmber = Color(0xFFF59E0B) // Amber 500
val SleekAmberDeep = Color(0xFFD97706) // Amber 600
val SleekAmberGlow = Color(0x66F59E0B) // rgba(245,158,11,0.4)
val SleekCrimson = Color(0xFFEF4444) // Red 500
val SleekCrimsonDeep = Color(0xFFDC2626) // Red 600

// Sleek Typography Slate Palette
val TextWhite = Color(0xFFFFFFFF)
val TextSlate200 = Color(0xFFE2E8F0)
val TextSlate400 = Color(0xFF94A3B8)
val TextSlate500 = Color(0xFF64748B)
val TextSlate600 = Color(0xFF475569)

// Theme compatibility aliases
val DarkBackground = SleekBackground
val DarkSurface = SleekSurface
val DarkSurfaceVariant = SleekSurfaceVariant
val DarkCardSurface = SleekCardSurface
val DarkCardBorder = SleekCardBorder

val CyberCyan = SleekBluePrimary
val CyberCyanDim = SleekBlueDark
val ShieldEmerald = SleekEmerald
val ShieldEmeraldDark = SleekEmeraldDark
val ElectricBlue = SleekBluePrimary
val NeonAmber = SleekAmber
val CrimsonRed = SleekCrimson

val TextPrimary = TextWhite
val TextSecondary = TextSlate400
val TextMuted = TextSlate500

val GlassSurface = SleekCardSurface
val GlassHighlight = SleekCardBorder
val GlassEmeraldHighlight = Color(0x3310B981)

