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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.ServerCategory
import com.example.model.ServerLocation
import com.example.ui.components.GlassCard
import com.example.ui.theme.SleekAmber
import com.example.ui.theme.SleekBackground
import com.example.ui.theme.SleekBluePrimary
import com.example.ui.theme.SleekCardBorder
import com.example.ui.theme.SleekCardSurface
import com.example.ui.theme.SleekEmerald
import com.example.ui.theme.SleekFlagBackground
import com.example.ui.theme.TextSlate400
import com.example.ui.theme.TextSlate500
import com.example.ui.theme.TextWhite
import com.example.viewmodel.VpnViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerListScreen(
    viewModel: VpnViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val servers by viewModel.filteredServers.collectAsState()
    val selectedServer by viewModel.selectedServer.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SleekBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.server_title),
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
                        modifier = Modifier.testTag("server_list_back_button")
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
        ) {
            // Search Input Field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                placeholder = {
                    Text(
                        text = stringResource(R.string.server_search_hint),
                        color = TextSlate500,
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = TextSlate500
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setSearchQuery("") }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Clear search",
                                tint = TextSlate400
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SleekBluePrimary,
                    unfocusedBorderColor = SleekCardBorder,
                    focusedContainerColor = SleekCardSurface,
                    unfocusedContainerColor = SleekCardSurface,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
                    .testTag("server_search_input")
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Category Tabs: Recommended, Free Servers, All Locations
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CategoryTabChip(
                    title = stringResource(R.string.tab_recommended),
                    isSelected = selectedCategory == ServerCategory.RECOMMENDED,
                    onClick = { viewModel.setSelectedCategory(ServerCategory.RECOMMENDED) },
                    modifier = Modifier.weight(1f)
                )

                CategoryTabChip(
                    title = stringResource(R.string.tab_free),
                    isSelected = selectedCategory == ServerCategory.FREE,
                    onClick = { viewModel.setSelectedCategory(ServerCategory.FREE) },
                    modifier = Modifier.weight(1f)
                )

                CategoryTabChip(
                    title = stringResource(R.string.tab_all),
                    isSelected = selectedCategory == ServerCategory.ALL,
                    onClick = { viewModel.setSelectedCategory(ServerCategory.ALL) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Servers List
            if (servers.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            tint = TextSlate500,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No server locations found",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextSlate400
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Try a different search query or category filter",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSlate500
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(servers, key = { it.id }) { server ->
                        val isSelected = server.id == selectedServer.id
                        ServerListItem(
                            server = server,
                            isSelected = isSelected,
                            onSelect = {
                                viewModel.selectServer(server)
                                onBackClick()
                            },
                            onToggleFavorite = {
                                viewModel.toggleFavorite(server.id)
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryTabChip(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) SleekBluePrimary else SleekCardSurface)
            .border(
                BorderStroke(1.dp, if (isSelected) SleekBluePrimary else SleekCardBorder),
                RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 11.sp
            ),
            color = if (isSelected) TextWhite else TextSlate400
        )
    }
}

@Composable
private fun ServerListItem(
    server: ServerLocation,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pingColor = when {
        server.pingMs < 50 -> SleekEmerald
        server.pingMs < 90 -> SleekAmber
        else -> Color(0xFFFF7043)
    }

    val borderColor = if (isSelected) SleekBluePrimary else SleekCardBorder

    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect)
            .testTag("server_item_${server.id}"),
        cornerRadius = 16.dp,
        borderColor = borderColor,
        backgroundColor = if (isSelected) SleekCardSurface.copy(alpha = 0.12f) else SleekCardSurface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Flag container (w-12 h-8 rounded bg-slate-800 border border-white/10)
                Box(
                    modifier = Modifier
                        .size(width = 46.dp, height = 32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SleekFlagBackground)
                        .border(BorderStroke(1.dp, SleekCardBorder), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = server.flagEmoji,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = server.country,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp
                            ),
                            color = if (isSelected) SleekBluePrimary else TextWhite
                        )
                        if (server.isFree) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SleekEmerald.copy(alpha = 0.15f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "FREE",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = SleekEmerald
                                )
                            }
                        }
                    }

                    Text(
                        text = "${server.city.uppercase()} • ${server.protocol}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.8.sp
                        ),
                        color = TextSlate400
                    )
                }
            }

            // Stats and Actions
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Latency & Load
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(end = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.SignalCellularAlt,
                            contentDescription = null,
                            tint = pingColor,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${server.pingMs} ms",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = pingColor
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "${server.loadPercentage}% load",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = TextSlate500
                    )
                }

                // Favorite Star
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(
                        imageVector = if (server.isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                        contentDescription = "Favorite",
                        tint = if (server.isFavorite) SleekAmber else TextSlate500,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Selection Radio / Checkmark
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(SleekBluePrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Selected",
                            tint = TextWhite,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

