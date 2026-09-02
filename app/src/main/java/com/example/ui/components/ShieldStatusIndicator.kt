package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GppBad
import androidx.compose.material.icons.filled.GppGood
import androidx.compose.material.icons.filled.GppMaybe
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SignalWifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.VpnState
import com.example.ui.theme.SleekAmber
import com.example.ui.theme.SleekBluePrimary
import com.example.ui.theme.SleekCardBorder
import com.example.ui.theme.SleekCardSurface
import com.example.ui.theme.SleekCrimson
import com.example.ui.theme.SleekEmerald
import com.example.ui.theme.TextSlate500
import com.example.ui.theme.TextWhite

@Composable
fun ShieldStatusIndicator(
    vpnState: VpnState,
    modifier: Modifier = Modifier
) {
    val targetColor = when (vpnState) {
        VpnState.CONNECTED -> SleekEmerald
        VpnState.CONNECTING, VpnState.DISCONNECTING -> SleekAmber
        VpnState.ERROR, VpnState.NO_INTERNET -> SleekCrimson
        VpnState.IDLE -> SleekBluePrimary
    }

    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(400),
        label = "status_color"
    )

    val statusTitle = when (vpnState) {
        VpnState.CONNECTED -> stringResource(R.string.status_connected)
        VpnState.CONNECTING -> stringResource(R.string.status_connecting)
        VpnState.DISCONNECTING -> stringResource(R.string.status_disconnecting)
        VpnState.ERROR -> stringResource(R.string.status_error)
        VpnState.NO_INTERNET -> stringResource(R.string.status_no_internet)
        VpnState.IDLE -> stringResource(R.string.status_disconnected)
    }

    val statusSubtitle = when (vpnState) {
        VpnState.CONNECTED -> "AES-256 Encrypted Tunnel Active"
        VpnState.CONNECTING -> "Securing your connection..."
        VpnState.DISCONNECTING -> "Disconnecting tunnel..."
        VpnState.ERROR -> "Connection attempt failed"
        VpnState.NO_INTERNET -> "No network connection detected"
        VpnState.IDLE -> "Your IP is visible to everyone"
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(110.dp),
            contentAlignment = Alignment.Center
        ) {
            // Ambient blur background aura
            Canvas(modifier = Modifier.size(110.dp)) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            animatedColor.copy(alpha = 0.22f),
                            Color.Transparent
                        ),
                        center = center,
                        radius = size.minDimension / 2
                    ),
                    radius = size.minDimension / 2,
                    center = center
                )
            }

            // Central circle icon container (w-24 h-24 bg-white/5 rounded-full border border-white/10)
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(SleekCardSurface)
                    .border(BorderStroke(1.dp, SleekCardBorder), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                val icon = when (vpnState) {
                    VpnState.CONNECTED -> Icons.Filled.GppGood
                    VpnState.CONNECTING, VpnState.DISCONNECTING -> Icons.Filled.GppMaybe
                    VpnState.ERROR -> Icons.Filled.GppBad
                    VpnState.NO_INTERNET -> Icons.Filled.SignalWifiOff
                    VpnState.IDLE -> Icons.Filled.Shield
                }

                Icon(
                    imageVector = icon,
                    contentDescription = statusTitle,
                    tint = animatedColor,
                    modifier = Modifier.size(46.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Headline
        Text(
            text = statusTitle,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                letterSpacing = (-0.5).sp
            ),
            color = TextWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Subtitle in slate-500
        Text(
            text = statusSubtitle,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp
            ),
            color = TextSlate500,
            textAlign = TextAlign.Center
        )
    }
}

