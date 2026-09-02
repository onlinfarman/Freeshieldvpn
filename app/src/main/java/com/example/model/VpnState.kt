package com.example.model

enum class VpnState {
    IDLE,          // Disconnected
    CONNECTING,    // Handshake in progress
    CONNECTED,     // Active & encrypted tunnel
    DISCONNECTING, // Gracefully tearing down tunnel
    ERROR,         // Server unreachable / auth error
    NO_INTERNET    // Network dropped
}
