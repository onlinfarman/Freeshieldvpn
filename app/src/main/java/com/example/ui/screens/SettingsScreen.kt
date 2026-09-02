package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import com.example.ui.components.GlassCard
import com.example.ui.theme.SleekAmber
import com.example.ui.theme.SleekBackground
import com.example.ui.theme.SleekBlueDark
import com.example.ui.theme.SleekBluePrimary
import com.example.ui.theme.SleekCardBorder
import com.example.ui.theme.SleekCardSurface
import com.example.ui.theme.SleekEmerald
import com.example.ui.theme.TextSlate400
import com.example.ui.theme.TextSlate500
import com.example.ui.theme.TextWhite
import com.example.viewmodel.VpnViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: VpnViewModel,
    onBackClick: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onNavigateToTerms: () -> Unit,
    modifier: Modifier = Modifier
) {
    val settings by viewModel.settings.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SleekBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings_title),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        color = TextWhite
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("settings_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SleekBackground
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // Category 1: Connection & Security
            SettingsSectionHeader(title = stringResource(R.string.settings_category_connection))

            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = 16.dp,
                backgroundColor = SleekCardSurface,
                borderColor = SleekCardBorder
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    // Operation Mode (Demo vs Live)
                    SettingModeSwitchRow(
                        mode = settings.vpnMode,
                        onModeToggle = {
                            val newMode = if (settings.vpnMode == VpnMode.DEMO) VpnMode.LIVE else VpnMode.DEMO
                            viewModel.updateVpnMode(newMode)
                        }
                    )

                    SettingsDivider()

                    // Auto Connect
                    SettingSwitchRow(
                        icon = Icons.Filled.Wifi,
                        iconTint = SleekBluePrimary,
                        title = stringResource(R.string.setting_auto_connect),
                        description = stringResource(R.string.setting_auto_connect_desc),
                        checked = settings.autoConnect,
                        onCheckedChange = { viewModel.updateAutoConnect(it) },
                        testTag = "setting_auto_connect_switch"
                    )

                    SettingsDivider()

                    // Kill Switch
                    SettingSwitchRow(
                        icon = Icons.Filled.Block,
                        iconTint = Color(0xFFEF4444),
                        title = stringResource(R.string.setting_kill_switch),
                        description = stringResource(R.string.setting_kill_switch_desc),
                        checked = settings.killSwitch,
                        onCheckedChange = { viewModel.updateKillSwitch(it) },
                        testTag = "setting_kill_switch_switch"
                    )

                    SettingsDivider()

                    // Connect on Boot
                    SettingSwitchRow(
                        icon = Icons.Filled.PowerSettingsNew,
                        iconTint = SleekEmerald,
                        title = stringResource(R.string.setting_boot_connect),
                        description = stringResource(R.string.setting_boot_connect_desc),
                        checked = settings.connectOnBoot,
                        onCheckedChange = { viewModel.updateConnectOnBoot(it) },
                        testTag = "setting_boot_connect_switch"
                    )

                    SettingsDivider()

                    // Encrypted DNS Protection
                    SettingSwitchRow(
                        icon = Icons.Filled.Dns,
                        iconTint = SleekBluePrimary,
                        title = stringResource(R.string.setting_dns_protection),
                        description = stringResource(R.string.setting_dns_protection_desc),
                        checked = settings.dnsProtection,
                        onCheckedChange = { viewModel.updateDnsProtection(it) },
                        testTag = "setting_dns_protection_switch"
                    )

                    SettingsDivider()

                    // Notifications
                    SettingSwitchRow(
                        icon = Icons.Filled.Notifications,
                        iconTint = SleekAmber,
                        title = stringResource(R.string.setting_notifications),
                        description = stringResource(R.string.setting_notifications_desc),
                        checked = settings.notifications,
                        onCheckedChange = { viewModel.updateNotifications(it) },
                        testTag = "setting_notifications_switch"
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Category 2: General
            SettingsSectionHeader(title = stringResource(R.string.settings_category_general))

            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = 16.dp,
                backgroundColor = SleekCardSurface,
                borderColor = SleekCardBorder
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    SettingNavigationRow(
                        icon = Icons.Filled.Palette,
                        iconTint = SleekBluePrimary,
                        title = stringResource(R.string.setting_theme),
                        value = stringResource(R.string.setting_theme_desc),
                        onClick = { /* Dark theme default */ }
                    )

                    SettingsDivider()

                    SettingNavigationRow(
                        icon = Icons.Filled.Language,
                        iconTint = SleekEmerald,
                        title = stringResource(R.string.setting_language),
                        value = stringResource(R.string.setting_language_desc),
                        onClick = { /* English default */ }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Category 3: About & Legal
            SettingsSectionHeader(title = stringResource(R.string.settings_category_about))

            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = 16.dp,
                backgroundColor = SleekCardSurface,
                borderColor = SleekCardBorder
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    SettingNavigationRow(
                        icon = Icons.Filled.Info,
                        iconTint = SleekBluePrimary,
                        title = stringResource(R.string.setting_about),
                        value = "Architecture & Protocols",
                        onClick = onNavigateToAbout
                    )

                    SettingsDivider()

                    SettingNavigationRow(
                        icon = Icons.Filled.PrivacyTip,
                        iconTint = SleekEmerald,
                        title = stringResource(R.string.setting_privacy_policy),
                        value = stringResource(R.string.setting_privacy_policy_desc),
                        onClick = onNavigateToPrivacy
                    )

                    SettingsDivider()

                    SettingNavigationRow(
                        icon = Icons.Filled.Tune,
                        iconTint = TextSlate400,
                        title = stringResource(R.string.setting_tos),
                        value = "Open Source License",
                        onClick = onNavigateToTerms
                    )

                    SettingsDivider()

                    SettingNavigationRow(
                        icon = Icons.Filled.CheckCircle,
                        iconTint = SleekEmerald,
                        title = stringResource(R.string.setting_version),
                        value = stringResource(R.string.setting_version_val),
                        onClick = { /* Info */ }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp,
            fontSize = 11.sp
        ),
        color = SleekBluePrimary,
        modifier = Modifier.padding(start = 6.dp, bottom = 8.dp)
    )
}

@Composable
private fun SettingSwitchRow(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(iconTint.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.padding(end = 8.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    ),
                    color = TextWhite
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp
                    ),
                    color = TextSlate400
                )
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = TextWhite,
                checkedTrackColor = SleekBlueDark,
                uncheckedThumbColor = TextSlate400,
                uncheckedTrackColor = Color(0x1AFFFFFF)
            ),
            modifier = Modifier.testTag(testTag)
        )
    }
}

@Composable
private fun SettingModeSwitchRow(
    mode: VpnMode,
    onModeToggle: () -> Unit
) {
    val isDemo = mode == VpnMode.DEMO
    val modeColor = if (isDemo) SleekAmber else SleekEmerald
    val modeName = if (isDemo) "Demo Mode (Testing)" else "Live VpnService Mode"
    val modeDesc = if (isDemo) {
        "UI simulation. No remote server tunnel active."
    } else {
        "Configures device TUN interface for remote server."
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onModeToggle)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(modeColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.SwapHoriz,
                    contentDescription = null,
                    tint = modeColor,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.padding(end = 8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = modeName,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                        color = modeColor
                    )
                }
                Text(
                    text = modeDesc,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = TextSlate400
                )
            }
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(modeColor.copy(alpha = 0.15f))
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                text = if (isDemo) "DEMO" else "LIVE",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                ),
                color = modeColor
            )
        }
    }
}

@Composable
private fun SettingNavigationRow(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(iconTint.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.padding(end = 8.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    ),
                    color = TextWhite
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp
                    ),
                    color = TextSlate400
                )
            }
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = TextSlate500,
            modifier = Modifier.size(14.dp)
        )
    }
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = SleekCardBorder,
        thickness = 1.dp
    )
}

