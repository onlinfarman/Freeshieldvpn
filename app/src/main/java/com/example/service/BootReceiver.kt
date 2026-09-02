package com.example.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.data.ServerRepository
import com.example.data.SettingsRepository
import com.example.model.VpnMode

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == "android.intent.action.QUICKBOOT_POWERON") {
            Log.d("BootReceiver", "Device boot completed, checking connect on boot preference")
            val settingsRepo = SettingsRepository(context)
            val settings = settingsRepo.settings.value

            if (settings.connectOnBoot) {
                val serverRepo = ServerRepository()
                val selectedServer = serverRepo.getServerById(settingsRepo.selectedServerId.value)
                val vpnManager = VpnManager.getInstance(context)
                vpnManager.connect(selectedServer, settings.vpnMode)
            }
        }
    }
}
