package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VpnLock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.VpnMode
import com.example.model.VpnState
import com.example.ui.components.ConnectionStatsCard
import com.example.ui.components.DemoModeBanner
import com.example.ui.components.ModeBadge
import com.example.ui.components.SelectedServerCard
import com.example.ui.components.ShieldStatusIndicator
import com.example.ui.components.VpnConnectButton
import com.example.ui.theme.SleekAmber
import com.example.ui.theme.SleekBackground
import com.example.ui.theme.SleekBlueDark
import com.example.ui.theme.SleekBluePrimary
import com.example.ui.theme.SleekCardBorder
import com.example.ui.theme.SleekCardSurface
import com.example.ui.theme.SleekEmerald
import com.example.ui.theme.SleekSurface
import com.example.ui.theme.TextSlate400
import com.example.ui.theme.TextSlate500
import com.example.ui.theme.TextWhite
import com.example.viewmodel.VpnViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: VpnViewModel,
    onNavigateToServers: () -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val vpnState by viewModel.vpnState.collectAsState()
    val vpnStats by viewModel.vpnStats.collectAsState()
    val selectedServer by viewModel.selectedServer.collectAsState()
    val settings by viewModel.settings.collectAsState()

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SleekBackground,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Sleek Logo icon (w-8 h-8 bg-blue-600 rounded-lg flex items-center justify-center)
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SleekBlueDark),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Shield,
                                contentDescription = null,
                                tint = TextWhite,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "FreeShield",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                ),
                                color = TextWhite
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "VPN",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 18.sp
                                ),
                                color = SleekBluePrimary
                            )
                        }
                    }
                },
                actions = {
                    ModeBadge(
                        vpnMode = settings.vpnMode,
                        onClick = {
                            val nextMode = if (settings.vpnMode == VpnMode.DEMO) VpnMode.LIVE else VpnMode.DEMO
                            viewModel.updateVpnMode(nextMode)
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Settings circle button (w-10 h-10 rounded-full bg-white/5 flex items-center justify-center)
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(SleekCardSurface)
                            .border(BorderStroke(1.dp, SleekCardBorder), CircleShape)
                            .clickable(onClick = onNavigateToSettings)
                            .testTag("settings_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(R.string.action_settings),
                            tint = TextSlate400,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SleekBackground
                )
            )
        },
        bottomBar = {
            // Sleek Interface Bottom Navigation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SleekSurface)
                    .border(BorderStroke(1.dp, SleekCardBorder), RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tab: Home (Active)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(SleekBluePrimary.copy(alpha = 0.12f))
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Home",
                        tint = SleekBluePrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "HOME",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            fontSize = 11.sp
                        ),
                        color = SleekBluePrimary
                    )
                }

                // Tab: Servers
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = onNavigateToServers)
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Public,
                        contentDescription = "Servers",
                        tint = TextSlate500,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "SERVERS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.sp,
                            fontSize = 11.sp
                        ),
                        color = TextSlate500
                    )
                }

                // Tab: Settings
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = onNavigateToSettings)
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings",
                        tint = TextSlate500,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "SETTINGS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.sp,
                            fontSize = 11.sp
                        ),
                        color = TextSlate500
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Mode Banner Notice (if in demo mode)
            DemoModeBanner(
                vpnMode = settings.vpnMode,
                onSwitchModeClick = {
                    viewModel.updateVpnMode(VpnMode.LIVE)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 1. Connection Status Section (Shield & Status Text)
            ShieldStatusIndicator(
                vpnState = vpnState,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 2. Main Large Connect Button (Concentric Rings)
            VpnConnectButton(
                vpnState = vpnState,
                onClick = { viewModel.toggleConnect() }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 3. Selected Server Card
            SelectedServerCard(
                server = selectedServer,
                onChangeClick = onNavigateToServers
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 4. Quick Info Bar (Dot indicator + Demo/Tunnel Status + Core version)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val dotColor = when {
                        vpnState == VpnState.CONNECTED -> SleekEmerald
                        settings.vpnMode == VpnMode.DEMO -> SleekAmber
                        else -> SleekBluePrimary
                    }
                    val statusLabel = when {
                        vpnState == VpnState.CONNECTED -> "PROTECTED"
                        settings.vpnMode == VpnMode.DEMO -> "DEMO MODE"
                        else -> "STANDBY"
                    }

                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(dotColor)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = statusLabel,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp,
                            fontSize = 10.sp
                        ),
                        color = TextSlate400
                    )
                }

                Text(
                    text = "FreeShield Core v1.4.2",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp
                    ),
                    color = TextSlate500
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 5. Connection Info Telemetry Card (Shown when connected)
            ConnectionStatsCard(
                stats = vpnStats,
                isVisible = vpnState == VpnState.CONNECTED
            )

            if (vpnState == VpnState.CONNECTED) {
                Spacer(modifier = Modifier.height(14.dp))
            }

            // 6. Quick Security Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                QuickFeatureChip(
                    icon = Icons.Filled.Lock,
                    label = "256-bit AES"
                )
                QuickFeatureChip(
                    icon = Icons.Filled.VpnLock,
                    label = "Zero Logs"
                )
                QuickFeatureChip(
                    icon = Icons.Filled.Security,
                    label = "DNS Guard"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun QuickFeatureChip(
    icon: ImageVector,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(SleekCardSurface)
            .border(BorderStroke(1.dp, SleekCardBorder), RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = SleekBluePrimary,
            modifier = Modifier.size(13.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
            color = TextSlate400
        )
    }
}

