package com.example.model

import java.util.Locale

data class VpnStats(
    val durationSeconds: Long = 0L,
    val downloadSpeedBps: Long = 0L,
    val uploadSpeedBps: Long = 0L,
    val totalBytesDown: Long = 0L,
    val totalBytesUp: Long = 0L,
    val virtualIp: String = "10.8.0.2"
) {
    fun formattedDuration(): String {
        val hours = durationSeconds / 3600
        val minutes = (durationSeconds % 3600) / 60
        val seconds = durationSeconds % 60
        return if (hours > 0) {
            String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format(Locale.US, "%02d:%02d", minutes, seconds)
        }
    }

    fun formattedDownloadSpeed(): String = formatSpeed(downloadSpeedBps)
    fun formattedUploadSpeed(): String = formatSpeed(uploadSpeedBps)
    fun formattedTotalData(): String = formatBytes(totalBytesDown + totalBytesUp)

    companion object {
        fun formatSpeed(bytesPerSec: Long): String {
            return when {
                bytesPerSec >= 1024 * 1024 -> String.format(Locale.US, "%.1f MB/s", bytesPerSec / (1024.0 * 1024.0))
                bytesPerSec >= 1024 -> String.format(Locale.US, "%.0f KB/s", bytesPerSec / 1024.0)
                else -> "$bytesPerSec B/s"
            }
        }

        fun formatBytes(bytes: Long): String {
            return when {
                bytes >= 1024 * 1024 * 1024 -> String.format(Locale.US, "%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0))
                bytes >= 1024 * 1024 -> String.format(Locale.US, "%.1f MB", bytes / (1024.0 * 1024.0))
                bytes >= 1024 -> String.format(Locale.US, "%.0f KB", bytes / 1024.0)
                else -> "$bytes B"
            }
        }
    }
}
