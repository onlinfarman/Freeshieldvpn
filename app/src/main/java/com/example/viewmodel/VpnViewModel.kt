package com.example.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.ServerCategory
import com.example.data.ServerRepository
import com.example.data.SettingsRepository
import com.example.model.AppSettings
import com.example.model.ServerLocation
import com.example.model.VpnMode
import com.example.model.VpnState
import com.example.model.VpnStats
import com.example.service.VpnManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VpnViewModel(
    application: Application,
    private val serverRepository: ServerRepository = ServerRepository(),
    private val settingsRepository: SettingsRepository = SettingsRepository(application)
) : AndroidViewModel(application) {

    private val vpnManager = VpnManager.getInstance(application)

    val vpnState: StateFlow<VpnState> = vpnManager.vpnState
    val vpnStats: StateFlow<VpnStats> = vpnManager.vpnStats
    val settings: StateFlow<AppSettings> = settingsRepository.settings
    val permissionRequest: kotlinx.coroutines.flow.SharedFlow<Intent> = vpnManager.vpnPermissionRequest

    val selectedServer: StateFlow<ServerLocation> = combine(
        serverRepository.servers,
        settingsRepository.selectedServerId
    ) { servers, selectedId ->
        servers.find { it.id == selectedId } ?: servers.first()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        serverRepository.getServerById(settingsRepository.selectedServerId.value)
    )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow(ServerCategory.RECOMMENDED)
    val selectedCategory: StateFlow<ServerCategory> = _selectedCategory.asStateFlow()

    val filteredServers: StateFlow<List<ServerLocation>> = combine(
        serverRepository.servers,
        _searchQuery,
        _selectedCategory
    ) { servers, query, category ->
        val listByCategory = when (category) {
            ServerCategory.RECOMMENDED -> servers.filter { it.isRecommended }
            ServerCategory.FREE -> servers.filter { it.isFree }
            ServerCategory.ALL -> servers
        }

        if (query.isBlank()) {
            listByCategory
        } else {
            val q = query.trim().lowercase()
            listByCategory.filter {
                it.country.lowercase().contains(q) ||
                        it.city.lowercase().contains(q) ||
                        it.countryCode.lowercase().contains(q)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun toggleConnect() {
        val currentState = vpnState.value
        val currentSettings = settings.value
        val server = selectedServer.value

        when (currentState) {
            VpnState.IDLE, VpnState.ERROR, VpnState.NO_INTERNET -> {
                vpnManager.connect(
                    server = server,
                    mode = currentSettings.vpnMode,
                    dns1 = currentSettings.primaryDns,
                    dns2 = currentSettings.secondaryDns
                )
            }
            VpnState.CONNECTED, VpnState.CONNECTING -> {
                vpnManager.disconnect(currentSettings.vpnMode)
            }
            VpnState.DISCONNECTING -> {
                // Ignore while in transition
            }
        }
    }

    fun onVpnPermissionGranted() {
        val currentSettings = settings.value
        val server = selectedServer.value
        vpnManager.startLiveVpnService(server, currentSettings.primaryDns, currentSettings.secondaryDns)
    }

    fun selectServer(server: ServerLocation) {
        settingsRepository.setSelectedServerId(server.id)
        // If currently connected, reconnect to the new server seamlessly
        if (vpnState.value == VpnState.CONNECTED) {
            vpnManager.disconnect(settings.value.vpnMode)
            viewModelScope.launch {
                kotlinx.coroutines.delay(700)
                vpnManager.connect(server, settings.value.vpnMode, settings.value.primaryDns, settings.value.secondaryDns)
            }
        }
    }

    fun toggleFavorite(serverId: String) {
        serverRepository.toggleFavorite(serverId)
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedCategory(category: ServerCategory) {
        _selectedCategory.value = category
    }

    fun updateAutoConnect(enabled: Boolean) = settingsRepository.updateAutoConnect(enabled)
    fun updateKillSwitch(enabled: Boolean) = settingsRepository.updateKillSwitch(enabled)
    fun updateConnectOnBoot(enabled: Boolean) = settingsRepository.updateConnectOnBoot(enabled)
    fun updateDnsProtection(enabled: Boolean) = settingsRepository.updateDnsProtection(enabled)
    fun updateNotifications(enabled: Boolean) = settingsRepository.updateNotifications(enabled)
    fun updateVpnMode(mode: VpnMode) = settingsRepository.updateVpnMode(mode)
    fun completeOnboarding() = settingsRepository.setCompletedOnboarding(true)

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VpnViewModel::class.java)) {
                return VpnViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
