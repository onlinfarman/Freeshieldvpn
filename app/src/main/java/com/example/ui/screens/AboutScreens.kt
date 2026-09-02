package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassCard
import com.example.ui.theme.SleekBackground
import com.example.ui.theme.SleekBlueDark
import com.example.ui.theme.SleekBluePrimary
import com.example.ui.theme.SleekCardBorder
import com.example.ui.theme.SleekCardSurface
import com.example.ui.theme.SleekEmerald
import com.example.ui.theme.TextSlate400
import com.example.ui.theme.TextSlate500
import com.example.ui.theme.TextWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SleekBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "About FreeShield VPN",
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
                        modifier = Modifier.testTag("about_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SleekBackground)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            // App Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(SleekBlueDark),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Shield,
                            contentDescription = null,
                            tint = TextWhite,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "FreeShield VPN",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = TextWhite
                    )
                    Text(
                        text = "Version 1.4.2 • Open Source Privacy Client",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSlate400
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Technical Architecture Explainer
            Text(
                text = "SYSTEM ARCHITECTURE & TUNNELING",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp,
                    fontSize = 11.sp
                ),
                color = SleekBluePrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = SleekCardSurface,
                borderColor = SleekCardBorder,
                cornerRadius = 16.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ArchitecturePoint(
                        icon = Icons.Filled.Code,
                        title = "Android VpnService API",
                        desc = "Creates an OS-level virtual TUN network interface with MTU 1500, routing all system IPv4 packets through a secure local file descriptor."
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    ArchitecturePoint(
                        icon = Icons.Filled.Lock,
                        title = "Encrypted Transport Socket",
                        desc = "The actual encrypted tunnel is established in FreeShieldVpnService.startTransportLayer() via protected UDP datagram sockets using ChaCha20-Poly1305 / AES-256-GCM."
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    ArchitecturePoint(
                        icon = Icons.Filled.Security,
                        title = "DNS & Leak Shielding",
                        desc = "Custom upstream DNS resolvers (1.1.1.1 / 9.9.9.9) prevent ISP monitoring and DNS poisoning at the root level."
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Modes Explanation
            Text(
                text = "OPERATING MODES",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp,
                    fontSize = 11.sp
                ),
                color = SleekEmerald
            )

            Spacer(modifier = Modifier.height(8.dp))

            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = SleekCardSurface,
                borderColor = SleekCardBorder,
                cornerRadius = 16.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "1. Live VpnService Mode",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = SleekEmerald
                    )
                    Text(
                        text = "Connects the virtual network interface to configured backend endpoints. Requires Android system VPN approval dialog.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSlate400
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "2. Demo Mode",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = SleekBluePrimary
                    )
                    Text(
                        text = "Provides complete UI simulation for test environments without mutating system network routing when backend is offline.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSlate400
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SleekBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Privacy Policy",
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
                        modifier = Modifier.testTag("privacy_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SleekBackground)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = SleekCardSurface,
                borderColor = SleekCardBorder,
                cornerRadius = 16.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.PrivacyTip,
                            contentDescription = null,
                            tint = SleekEmerald,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Strict Zero-Logs Commitment",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = SleekEmerald
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "FreeShield VPN operates on a strict No-Logs architecture. We do NOT collect, monitor, store, or share:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextWhite
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    PolicyBullet(text = "No browsing history or visited destinations")
                    PolicyBullet(text = "No DNS query logs or metadata")
                    PolicyBullet(text = "No real IP addresses or connection timestamps")
                    PolicyBullet(text = "No user identity tracking or device advertising IDs")
                    PolicyBullet(text = "No traffic payload interception or inspection")

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "All cryptography runs on end-to-end industry standards. Your personal data remains solely on your device.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSlate400
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SleekBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Terms of Service",
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
                        modifier = Modifier.testTag("terms_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SleekBackground)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = SleekCardSurface,
                borderColor = SleekCardBorder,
                cornerRadius = 16.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Open Source License & Fair Use",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "FreeShield VPN is provided under the permissive open-source license. The software is provided 'as is' without warranty of any kind.\n\n" +
                                "1. Lawful Usage: You agree not to use FreeShield VPN for illegal activities or malicious network disruptions.\n\n" +
                                "2. Fair Bandwidth: Free public nodes are shared community resources intended for private browsing and data encryption.\n\n" +
                                "3. Freedom of Configuration: You may connect FreeShield VPN to your own self-hosted VPS servers.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSlate400
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun ArchitecturePoint(
    icon: ImageVector,
    title: String,
    desc: String
) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(SleekBluePrimary.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SleekBluePrimary,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = TextWhite
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = TextSlate400
            )
        }
    }
}

@Composable
private fun PolicyBullet(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 3.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = SleekEmerald,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = TextSlate400
        )
    }
}

