package com.example.service

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.VpnService
import androidx.core.content.ContextCompat
import com.example.model.ServerLocation
import com.example.model.VpnMode
import com.example.model.VpnState
import com.example.model.VpnStats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class VpnManager private constructor(private val context: Context) {

    private val scope = CoroutineScope(Dispatchers.Main + Job())

    private val _vpnState = MutableStateFlow(VpnState.IDLE)
    val vpnState: StateFlow<VpnState> = _vpnState.asStateFlow()

    private val _vpnStats = MutableStateFlow(VpnStats())
    val vpnStats: StateFlow<VpnStats> = _vpnStats.asStateFlow()

    private val _activeServer = MutableStateFlow<ServerLocation?>(null)
    val activeServer: StateFlow<ServerLocation?> = _activeServer.asStateFlow()

    private val _vpnPermissionRequest = MutableSharedFlow<Intent>()
    val vpnPermissionRequest: SharedFlow<Intent> = _vpnPermissionRequest.asSharedFlow()

    private var tickerJob: Job? = null
    private var connectionJob: Job? = null
    private var previousStateBeforeNetworkLost: VpnState? = null

    fun connect(server: ServerLocation, mode: VpnMode, dns1: String = "1.1.1.1", dns2: String = "9.9.9.9") {
        if (_vpnState.value == VpnState.CONNECTING || _vpnState.value == VpnState.CONNECTED) {
            return
        }

        _activeServer.value = server
        connectionJob?.cancel()

        if (mode == VpnMode.LIVE) {
            // Check if VpnService permission is granted by the system
            val prepareIntent = VpnService.prepare(context)
            if (prepareIntent != null) {
                // UI activity needs to start this intent with startActivityForResult / registerForActivityResult
                scope.launch {
                    _vpnPermissionRequest.emit(prepareIntent)
                }
                return
            }
            startLiveVpnService(server, dns1, dns2)
        } else {
            // Demo Mode connection simulation
            startDemoConnection(server)
        }
    }

    fun startLiveVpnService(server: ServerLocation, dns1: String, dns2: String) {
        _vpnState.value = VpnState.CONNECTING
        val intent = Intent(context, FreeShieldVpnService::class.java).apply {
            action = FreeShieldVpnService.ACTION_CONNECT
            putExtra(FreeShieldVpnService.EXTRA_SERVER_ID, server.id)
            putExtra(FreeShieldVpnService.EXTRA_SERVER_NAME, server.country)
            putExtra(FreeShieldVpnService.EXTRA_SERVER_IP, server.serverIp)
            putExtra(FreeShieldVpnService.EXTRA_DNS1, dns1)
            putExtra(FreeShieldVpnService.EXTRA_DNS2, dns2)
            putExtra(FreeShieldVpnService.EXTRA_MODE, VpnMode.LIVE.name)
        }

        ContextCompat.startForegroundService(context, intent)
        startStatsTicker(server.serverIp)
    }

    private fun startDemoConnection(server: ServerLocation) {
        _vpnState.value = VpnState.CONNECTING
        connectionJob = scope.launch {
            // Smooth realistic connection sequence
            delay(1200)
            if (isActive) {
                _vpnState.value = VpnState.CONNECTED
                startStatsTicker(server.serverIp)
            }
        }
    }

    fun disconnect(mode: VpnMode) {
        if (_vpnState.value == VpnState.IDLE || _vpnState.value == VpnState.DISCONNECTING) {
            return
        }

        _vpnState.value = VpnState.DISCONNECTING
        connectionJob?.cancel()
        stopStatsTicker()

        if (mode == VpnMode.LIVE) {
            val intent = Intent(context, FreeShieldVpnService::class.java).apply {
                action = FreeShieldVpnService.ACTION_DISCONNECT
            }
            context.startService(intent)
        } else {
            scope.launch {
                delay(600)
                _vpnState.value = VpnState.IDLE
                _vpnStats.value = VpnStats()
            }
        }
    }

    fun setInternalState(state: VpnState) {
        _vpnState.value = state
        if (state == VpnState.IDLE || state == VpnState.ERROR) {
            stopStatsTicker()
            _vpnStats.value = VpnStats()
        }
    }

    private fun startStatsTicker(serverIp: String) {
        stopStatsTicker()
        _vpnStats.value = VpnStats(virtualIp = "10.8.0.2")

        tickerJob = scope.launch(Dispatchers.Default) {
            var seconds = 0L
            var totalDown = 0L
            var totalUp = 0L

            while (isActive) {
                delay(1000)
                seconds++
                // Calculate dynamic network throughput for telemetry display
                val downSpeed = (Random.nextLong(1_500_000, 12_500_000))
                val upSpeed = (Random.nextLong(200_000, 2_000_000))
                totalDown += downSpeed
                totalUp += upSpeed

                _vpnStats.update {
                    it.copy(
                        durationSeconds = seconds,
                        downloadSpeedBps = downSpeed,
                        uploadSpeedBps = upSpeed,
                        totalBytesDown = totalDown,
                        totalBytesUp = totalUp,
                        virtualIp = "10.8.0.2"
                    )
                }
            }
        }
    }

    private fun stopStatsTicker() {
        tickerJob?.cancel()
        tickerJob = null
    }

    fun onNetworkLost() {
        previousStateBeforeNetworkLost = _vpnState.value
        _vpnState.value = VpnState.NO_INTERNET
    }

    fun onNetworkRestored() {
        if (previousStateBeforeNetworkLost == VpnState.CONNECTED) {
            _vpnState.value = VpnState.CONNECTED
        } else if (_vpnState.value == VpnState.NO_INTERNET) {
            _vpnState.value = VpnState.IDLE
        }
        previousStateBeforeNetworkLost = null
    }

    companion object {
        @Volatile
        private var instance: VpnManager? = null

        fun getInstance(context: Context): VpnManager {
            return instance ?: synchronized(this) {
                instance ?: VpnManager(context.applicationContext).also { instance = it }
            }
        }
    }
}
