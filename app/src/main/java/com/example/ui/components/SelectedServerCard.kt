package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ServerLocation
import com.example.ui.theme.SleekAmber
import com.example.ui.theme.SleekCardBorder
import com.example.ui.theme.SleekCardSurface
import com.example.ui.theme.SleekEmerald
import com.example.ui.theme.SleekFlagBackground
import com.example.ui.theme.TextSlate400
import com.example.ui.theme.TextSlate500
import com.example.ui.theme.TextWhite

@Composable
fun SelectedServerCard(
    server: ServerLocation,
    onChangeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pingColor = when {
        server.pingMs < 50 -> SleekEmerald
        server.pingMs < 90 -> SleekAmber
        else -> Color(0xFFFF7043)
    }

    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .testTag("selected_server_card")
            .clickable(onClick = onChangeClick),
        cornerRadius = 18.dp,
        backgroundColor = SleekCardSurface,
        borderColor = SleekCardBorder
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Flag badge container (w-12 h-8 rounded bg-slate-800 border border-white/10)
                Box(
                    modifier = Modifier
                        .size(width = 48.dp, height = 34.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SleekFlagBackground)
                        .border(BorderStroke(1.dp, SleekCardBorder), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = server.flagEmoji,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = server.country,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        ),
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${server.city.uppercase()} • ${server.pingMs}MS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.sp
                        ),
                        color = TextSlate400
                    )
                }
            }

            // Right side arrow chevron
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = stringResource(R.string.action_change_server),
                tint = TextSlate500,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

