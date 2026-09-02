package com.example.model

data class AppSettings(
    val autoConnect: Boolean = false,
    val killSwitch: Boolean = false,
    val connectOnBoot: Boolean = false,
    val dnsProtection: Boolean = true,
    val notifications: Boolean = true,
    val vpnMode: VpnMode = VpnMode.DEMO,
    val primaryDns: String = "1.1.1.1",
    val secondaryDns: String = "9.9.9.9",
    val hasCompletedOnboarding: Boolean = false
)
