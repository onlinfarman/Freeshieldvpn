package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.VpnMode
import com.example.ui.theme.SleekAmber
import com.example.ui.theme.SleekBluePrimary
import com.example.ui.theme.SleekCardBorder
import com.example.ui.theme.SleekCardSurface
import com.example.ui.theme.SleekEmerald
import com.example.ui.theme.TextSlate400
import com.example.ui.theme.TextSlate500

@Composable
fun ModeBadge(
    vpnMode: VpnMode,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDemo = vpnMode == VpnMode.DEMO
    val badgeColor = if (isDemo) SleekAmber else SleekEmerald
    val badgeText = if (isDemo) stringResource(R.string.demo_badge) else stringResource(R.string.live_badge)

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(badgeColor.copy(alpha = 0.12f))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .testTag("mode_badge_button"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(badgeColor)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = badgeText,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp,
                fontSize = 11.sp
            ),
            color = badgeColor
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            imageVector = Icons.Filled.SwapHoriz,
            contentDescription = "Switch Mode",
            tint = badgeColor,
            modifier = Modifier.size(12.dp)
        )
    }
}

@Composable
fun DemoModeBanner(
    vpnMode: VpnMode,
    onSwitchModeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (vpnMode == VpnMode.DEMO) {
        GlassCard(
            modifier = modifier.fillMaxWidth(),
            cornerRadius = 14.dp,
            backgroundColor = SleekCardSurface,
            borderColor = SleekCardBorder
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                    tint = SleekAmber,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.demo_mode_title),
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = SleekAmber
                    )
                    Text(
                        text = "Demo Mode — Safe simulation active.",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = TextSlate400
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Switch",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = SleekBluePrimary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SleekBluePrimary.copy(alpha = 0.15f))
                        .clickable(onClick = onSwitchModeClick)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
        }
    }
}

