package com.example.data

import com.example.model.ServerLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class ServerCategory {
    RECOMMENDED,
    FREE,
    ALL
}

class ServerRepository {

    private val defaultServers = listOf(
        ServerLocation(
            id = "us-east-ny",
            country = "United States",
            countryCode = "US",
            city = "New York",
            flagEmoji = "🇺🇸",
            serverIp = "198.51.100.42",
            pingMs = 28,
            loadPercentage = 42,
            isFree = true,
            isRecommended = true,
            isFavorite = true,
            protocol = "WireGuard / OpenVPN"
        ),
        ServerLocation(
            id = "us-west-la",
            country = "United States",
            countryCode = "US",
            city = "Los Angeles",
            flagEmoji = "🇺🇸",
            serverIp = "198.51.100.45",
            pingMs = 38,
            loadPercentage = 55,
            isFree = true,
            isRecommended = false,
            protocol = "WireGuard"
        ),
        ServerLocation(
            id = "uk-lon",
            country = "United Kingdom",
            countryCode = "GB",
            city = "London",
            flagEmoji = "🇬🇧",
            serverIp = "203.0.113.12",
            pingMs = 45,
            loadPercentage = 38,
            isFree = true,
            isRecommended = true,
            isFavorite = true,
            protocol = "WireGuard / OpenVPN"
        ),
        ServerLocation(
            id = "de-fra",
            country = "Germany",
            countryCode = "DE",
            city = "Frankfurt",
            flagEmoji = "🇩🇪",
            serverIp = "203.0.113.88",
            pingMs = 52,
            loadPercentage = 48,
            isFree = true,
            isRecommended = true,
            protocol = "OpenVPN"
        ),
        ServerLocation(
            id = "nl-ams",
            country = "Netherlands",
            countryCode = "NL",
            city = "Amsterdam",
            flagEmoji = "🇳🇱",
            serverIp = "198.51.100.99",
            pingMs = 35,
            loadPercentage = 31,
            isFree = true,
            isRecommended = true,
            protocol = "WireGuard"
        ),
        ServerLocation(
            id = "sg-sin",
            country = "Singapore",
            countryCode = "SG",
            city = "Singapore City",
            flagEmoji = "🇸🇬",
            serverIp = "203.0.113.150",
            pingMs = 68,
            loadPercentage = 62,
            isFree = true,
            isRecommended = true,
            protocol = "WireGuard / OpenVPN"
        ),
        ServerLocation(
            id = "jp-tyo",
            country = "Japan",
            countryCode = "JP",
            city = "Tokyo",
            flagEmoji = "🇯🇵",
            serverIp = "198.51.100.210",
            pingMs = 85,
            loadPercentage = 50,
            isFree = true,
            isRecommended = false,
            protocol = "WireGuard"
        ),
        ServerLocation(
            id = "pk-khi",
            country = "Pakistan",
            countryCode = "PK",
            city = "Karachi",
            flagEmoji = "🇵🇰",
            serverIp = "203.0.113.77",
            pingMs = 92,
            loadPercentage = 44,
            isFree = true,
            isRecommended = true,
            protocol = "OpenVPN"
        ),
        ServerLocation(
            id = "ca-tor",
            country = "Canada",
            countryCode = "CA",
            city = "Toronto",
            flagEmoji = "🇨🇦",
            serverIp = "198.51.100.64",
            pingMs = 40,
            loadPercentage = 35,
            isFree = true,
            isRecommended = false,
            protocol = "WireGuard"
        ),
        ServerLocation(
            id = "au-syd",
            country = "Australia",
            countryCode = "AU",
            city = "Sydney",
            flagEmoji = "🇦🇺",
            serverIp = "203.0.113.230",
            pingMs = 120,
            loadPercentage = 29,
            isFree = true,
            isRecommended = false,
            protocol = "WireGuard"
        ),
        ServerLocation(
            id = "fr-par",
            country = "France",
            countryCode = "FR",
            city = "Paris",
            flagEmoji = "🇫🇷",
            serverIp = "198.51.100.18",
            pingMs = 49,
            loadPercentage = 58,
            isFree = true,
            isRecommended = false,
            protocol = "OpenVPN"
        )
    )

    private val _servers = MutableStateFlow(defaultServers)
    val servers: StateFlow<List<ServerLocation>> = _servers.asStateFlow()

    fun getServerById(id: String): ServerLocation {
        return _servers.value.find { it.id == id } ?: _servers.value.first()
    }

    fun getOptimalServer(): ServerLocation {
        return _servers.value.minByOrNull { it.pingMs } ?: _servers.value.first()
    }

    fun toggleFavorite(serverId: String) {
        _servers.update { list ->
            list.map {
                if (it.id == serverId) it.copy(isFavorite = !it.isFavorite) else it
            }
        }
    }

    fun addCustomServer(server: ServerLocation) {
        _servers.update { list ->
            listOf(server) + list.filter { it.id != server.id }
        }
    }

    fun filterServers(category: ServerCategory, query: String): List<ServerLocation> {
        val current = _servers.value
        val filteredByCategory = when (category) {
            ServerCategory.RECOMMENDED -> current.filter { it.isRecommended }
            ServerCategory.FREE -> current.filter { it.isFree }
            ServerCategory.ALL -> current
        }

        if (query.isBlank()) return filteredByCategory

        val cleanQuery = query.trim().lowercase()
        return filteredByCategory.filter {
            it.country.lowercase().contains(cleanQuery) ||
                    it.city.lowercase().contains(cleanQuery) ||
                    it.countryCode.lowercase().contains(cleanQuery)
        }
    }
}
