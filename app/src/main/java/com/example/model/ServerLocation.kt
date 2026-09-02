package com.example.model

data class ServerLocation(
    val id: String,
    val country: String,
    val countryCode: String,
    val city: String,
    val flagEmoji: String,
    val serverIp: String,
    val pingMs: Int,
    val loadPercentage: Int,
    val isFree: Boolean = true,
    val isRecommended: Boolean = false,
    val isFavorite: Boolean = false,
    val protocol: String = "OpenVPN / WireGuard",
    val port: Int = 1194,
    val publicKey: String = ""
)
