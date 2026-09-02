package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.VpnState
import com.example.ui.theme.SleekAmber
import com.example.ui.theme.SleekAmberDeep
import com.example.ui.theme.SleekAmberGlow
import com.example.ui.theme.SleekBlueBorderMiddle
import com.example.ui.theme.SleekBlueBorderOuter
import com.example.ui.theme.SleekBlueDeep
import com.example.ui.theme.SleekBlueGlow
import com.example.ui.theme.SleekBluePrimary
import com.example.ui.theme.SleekBlueRingMiddle
import com.example.ui.theme.SleekBlueRingOuter
import com.example.ui.theme.SleekCrimson
import com.example.ui.theme.SleekCrimsonDeep
import com.example.ui.theme.SleekEmerald
import com.example.ui.theme.SleekEmeraldDark
import com.example.ui.theme.SleekEmeraldDeep
import com.example.ui.theme.SleekEmeraldGlow
import com.example.ui.theme.TextWhite

@Composable
fun VpnConnectButton(
    vpnState: VpnState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "sleek_pulse")

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "button_scale"
    )

    val spinAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "spin_angle"
    )

    val buttonText = when (vpnState) {
        VpnState.CONNECTED -> stringResource(R.string.action_disconnect)
        VpnState.CONNECTING, VpnState.DISCONNECTING -> stringResource(R.string.action_connecting)
        VpnState.IDLE, VpnState.ERROR, VpnState.NO_INTERNET -> stringResource(R.string.action_connect)
    }

    // Gradient colors based on VPN status
    val buttonGradient = when (vpnState) {
        VpnState.CONNECTED -> Brush.linearGradient(
            listOf(SleekEmerald, SleekEmeraldDeep)
        )
        VpnState.CONNECTING, VpnState.DISCONNECTING -> Brush.linearGradient(
            listOf(SleekAmber, SleekAmberDeep)
        )
        VpnState.ERROR, VpnState.NO_INTERNET -> Brush.linearGradient(
            listOf(SleekCrimson, SleekCrimsonDeep)
        )
        VpnState.IDLE -> Brush.linearGradient(
            listOf(SleekBluePrimary, SleekBlueDeep)
        )
    }

    val shadowColor = when (vpnState) {
        VpnState.CONNECTED -> SleekEmeraldGlow
        VpnState.CONNECTING, VpnState.DISCONNECTING -> SleekAmberGlow
        VpnState.ERROR, VpnState.NO_INTERNET -> Color(0x66EF4444)
        VpnState.IDLE -> SleekBlueGlow
    }

    val ringOuterBg = when (vpnState) {
        VpnState.CONNECTED -> SleekEmerald.copy(alpha = 0.05f)
        VpnState.CONNECTING, VpnState.DISCONNECTING -> SleekAmber.copy(alpha = 0.05f)
        else -> SleekBlueRingOuter
    }

    val ringOuterBorder = when (vpnState) {
        VpnState.CONNECTED -> SleekEmerald.copy(alpha = 0.12f)
        VpnState.CONNECTING, VpnState.DISCONNECTING -> SleekAmber.copy(alpha = 0.12f)
        else -> SleekBlueBorderOuter
    }

    val ringMiddleBg = when (vpnState) {
        VpnState.CONNECTED -> SleekEmerald.copy(alpha = 0.10f)
        VpnState.CONNECTING, VpnState.DISCONNECTING -> SleekAmber.copy(alpha = 0.10f)
        else -> SleekBlueRingMiddle
    }

    val ringMiddleBorder = when (vpnState) {
        VpnState.CONNECTED -> SleekEmerald.copy(alpha = 0.22f)
        VpnState.CONNECTING, VpnState.DISCONNECTING -> SleekAmber.copy(alpha = 0.22f)
        else -> SleekBlueBorderMiddle
    }

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .size(240.dp)
            .testTag("vpn_connect_button"),
        contentAlignment = Alignment.Center
    ) {
        // Outer concentric ring (w-64 h-64 bg-blue-600/5 border-blue-500/10)
        Box(
            modifier = Modifier
                .size(236.dp)
                .clip(CircleShape)
                .background(ringOuterBg)
                .border(BorderStroke(1.dp, ringOuterBorder), CircleShape)
        )

        // Middle concentric ring (w-52 h-52 bg-blue-600/10 border-blue-500/20)
        Box(
            modifier = Modifier
                .size(195.dp)
                .clip(CircleShape)
                .background(ringMiddleBg)
                .border(BorderStroke(1.dp, ringMiddleBorder), CircleShape)
        )

        // Main Sleek Connect Button (w-44 h-44 rounded-full bg-gradient-to-br from-blue-500 to-blue-700 shadow-[0_20px_50px_rgba(37,99,235,0.4)] border-4 border-white/10)
        Box(
            modifier = Modifier
                .size(160.dp)
                .scale(if (vpnState == VpnState.CONNECTING) pulseScale else 1f)
                .shadow(
                    elevation = 20.dp,
                    shape = CircleShape,
                    ambientColor = shadowColor,
                    spotColor = shadowColor
                )
                .clip(CircleShape)
                .background(buttonGradient)
                .border(BorderStroke(4.dp, Color(0x26FFFFFF)), CircleShape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = ripple(bounded = true, color = TextWhite),
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = if (vpnState == VpnState.CONNECTED) Icons.Filled.PowerSettingsNew else Icons.Filled.Bolt,
                    contentDescription = buttonText,
                    tint = TextWhite,
                    modifier = Modifier
                        .size(46.dp)
                        .then(
                            if (vpnState == VpnState.CONNECTING) Modifier.rotate(spinAngle) else Modifier
                        )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = buttonText.uppercase(),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                        fontSize = 12.sp
                    ),
                    color = TextWhite
                )
            }
        }
    }
}

