package com.example.model

enum class VpnMode {
    DEMO, // Demo Mode: For testing and UI verification (transparently labeled)
    LIVE  // Real Android VpnService Mode: Configures device TUN interface
}
