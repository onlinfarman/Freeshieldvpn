package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.VpnStats
import com.example.ui.theme.SleekBluePrimary
import com.example.ui.theme.SleekCardBorder
import com.example.ui.theme.SleekCardSurface
import com.example.ui.theme.SleekEmerald
import com.example.ui.theme.TextSlate400
import com.example.ui.theme.TextSlate500
import com.example.ui.theme.TextWhite

@Composable
fun ConnectionStatsCard(
    stats: VpnStats,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically(initialOffsetY = { 30 }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { 30 })
    ) {
        GlassCard(
            modifier = modifier.fillMaxWidth(),
            cornerRadius = 18.dp,
            backgroundColor = SleekCardSurface,
            borderColor = SleekCardBorder
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Top Header: Duration & Virtual IP
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(SleekEmerald.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Timer,
                                contentDescription = null,
                                tint = SleekEmerald,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = stringResource(R.string.stat_duration),
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSlate500
                            )
                            Text(
                                text = stats.formattedDuration(),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = TextWhite
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(SleekBluePrimary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Public,
                                contentDescription = null,
                                tint = SleekBluePrimary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = stringResource(R.string.stat_ip_address),
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSlate500
                            )
                            Text(
                                text = stats.virtualIp,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = SleekBluePrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = SleekCardBorder, thickness = 1.dp)
                Spacer(modifier = Modifier.height(14.dp))

                // Stats Matrix: Download Speed, Upload Speed, Data Transferred
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatMetricItem(
                        icon = Icons.Filled.ArrowDownward,
                        iconTint = SleekEmerald,
                        label = stringResource(R.string.stat_download),
                        value = stats.formattedDownloadSpeed(),
                        modifier = Modifier.weight(1f)
                    )

                    StatMetricItem(
                        icon = Icons.Filled.ArrowUpward,
                        iconTint = SleekBluePrimary,
                        label = stringResource(R.string.stat_upload),
                        value = stats.formattedUploadSpeed(),
                        modifier = Modifier.weight(1f)
                    )

                    StatMetricItem(
                        icon = Icons.Filled.DataUsage,
                        iconTint = SleekBluePrimary,
                        label = stringResource(R.string.stat_data_used),
                        value = stats.formattedTotalData(),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Security guarantee banner
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0x0DFFFFFF))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                        tint = SleekEmerald,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "AES-256 GCM Tunnel Active • DNS Leak Protected",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                        color = TextSlate400
                    )
                }
            }
        }
    }
}

@Composable
private fun StatMetricItem(
    icon: ImageVector,
    iconTint: Color,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = TextSlate500
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = TextWhite
        )
    }
}

