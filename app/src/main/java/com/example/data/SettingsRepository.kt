package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.AppSettings
import com.example.model.VpnMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("freeshield_vpn_prefs", Context.MODE_PRIVATE)

    private val _settings = MutableStateFlow(loadSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    private val _selectedServerId = MutableStateFlow(
        prefs.getString(KEY_SELECTED_SERVER, "us-east-ny") ?: "us-east-ny"
    )
    val selectedServerId: StateFlow<String> = _selectedServerId.asStateFlow()

    private fun loadSettings(): AppSettings {
        val modeStr = prefs.getString(KEY_VPN_MODE, VpnMode.DEMO.name) ?: VpnMode.DEMO.name
        val mode = try {
            VpnMode.valueOf(modeStr)
        } catch (_: Exception) {
            VpnMode.DEMO
        }

        return AppSettings(
            autoConnect = prefs.getBoolean(KEY_AUTO_CONNECT, false),
            killSwitch = prefs.getBoolean(KEY_KILL_SWITCH, false),
            connectOnBoot = prefs.getBoolean(KEY_CONNECT_ON_BOOT, false),
            dnsProtection = prefs.getBoolean(KEY_DNS_PROTECTION, true),
            notifications = prefs.getBoolean(KEY_NOTIFICATIONS, true),
            vpnMode = mode,
            primaryDns = prefs.getString(KEY_PRIMARY_DNS, "1.1.1.1") ?: "1.1.1.1",
            secondaryDns = prefs.getString(KEY_SECONDARY_DNS, "9.9.9.9") ?: "9.9.9.9",
            hasCompletedOnboarding = prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
        )
    }

    fun updateAutoConnect(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_AUTO_CONNECT, enabled).apply()
        _settings.update { it.copy(autoConnect = enabled) }
    }

    fun updateKillSwitch(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_KILL_SWITCH, enabled).apply()
        _settings.update { it.copy(killSwitch = enabled) }
    }

    fun updateConnectOnBoot(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_CONNECT_ON_BOOT, enabled).apply()
        _settings.update { it.copy(connectOnBoot = enabled) }
    }

    fun updateDnsProtection(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_DNS_PROTECTION, enabled).apply()
        _settings.update { it.copy(dnsProtection = enabled) }
    }

    fun updateNotifications(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_NOTIFICATIONS, enabled).apply()
        _settings.update { it.copy(notifications = enabled) }
    }

    fun updateVpnMode(mode: VpnMode) {
        prefs.edit().putString(KEY_VPN_MODE, mode.name).apply()
        _settings.update { it.copy(vpnMode = mode) }
    }

    fun setCompletedOnboarding(completed: Boolean) {
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, completed).apply()
        _settings.update { it.copy(hasCompletedOnboarding = completed) }
    }

    fun setSelectedServerId(id: String) {
        prefs.edit().putString(KEY_SELECTED_SERVER, id).apply()
        _selectedServerId.value = id
    }

    companion object {
        private const val KEY_AUTO_CONNECT = "pref_auto_connect"
        private const val KEY_KILL_SWITCH = "pref_kill_switch"
        private const val KEY_CONNECT_ON_BOOT = "pref_connect_on_boot"
        private const val KEY_DNS_PROTECTION = "pref_dns_protection"
        private const val KEY_NOTIFICATIONS = "pref_notifications"
        private const val KEY_VPN_MODE = "pref_vpn_mode"
        private const val KEY_PRIMARY_DNS = "pref_primary_dns"
        private const val KEY_SECONDARY_DNS = "pref_secondary_dns"
        private const val KEY_ONBOARDING_COMPLETED = "pref_onboarding_completed"
        private const val KEY_SELECTED_SERVER = "pref_selected_server"
    }
}
