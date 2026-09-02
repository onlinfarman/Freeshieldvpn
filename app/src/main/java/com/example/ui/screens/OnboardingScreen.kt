package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VpnLock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
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
import kotlinx.coroutines.launch

data class OnboardingPageData(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val accentColor: Color
)

@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pages = listOf(
        OnboardingPageData(
            title = stringResource(R.string.onboarding_1_title),
            description = stringResource(R.string.onboarding_1_desc),
            icon = Icons.Filled.VpnLock,
            accentColor = SleekBluePrimary
        ),
        OnboardingPageData(
            title = stringResource(R.string.onboarding_2_title),
            description = stringResource(R.string.onboarding_2_desc),
            icon = Icons.Filled.Lock,
            accentColor = SleekEmerald
        ),
        OnboardingPageData(
            title = stringResource(R.string.onboarding_3_title),
            description = stringResource(R.string.onboarding_3_desc),
            icon = Icons.Filled.PowerSettingsNew,
            accentColor = SleekBlueDark
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SleekBackground)
    ) {
        // Skip Button Top Right
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, end = 20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            if (pagerState.currentPage < pages.size - 1) {
                TextButton(
                    onClick = onFinishOnboarding,
                    modifier = Modifier.testTag("onboarding_skip_button")
                ) {
                    Text(
                        text = stringResource(R.string.action_skip),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = TextSlate400
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(48.dp))
            }
        }

        // Pager Content
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 90.dp, bottom = 140.dp)
        ) { pageIndex ->
            val page = pages[pageIndex]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Hero Illustration Graphic Card
                Box(
                    modifier = Modifier
                        .size(170.dp)
                        .clip(CircleShape)
                        .background(page.accentColor.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape)
                            .background(page.accentColor.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = page.icon,
                            contentDescription = page.title,
                            tint = page.accentColor,
                            modifier = Modifier.size(68.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                Text(
                    text = page.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    ),
                    color = TextWhite,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = page.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSlate400,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }
        }

        // Bottom Bar with Dots & Next / Get Started Action
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Indicator dots
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(pages.size) { iteration ->
                    val isSelected = pagerState.currentPage == iteration
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (isSelected) SleekBluePrimary else SleekCardBorder)
                            .size(width = if (isSelected) 24.dp else 8.dp, height = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Button
            val isLastPage = pagerState.currentPage == pages.size - 1
            Button(
                onClick = {
                    if (isLastPage) {
                        onFinishOnboarding()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SleekBlueDark,
                    contentColor = TextWhite
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("onboarding_next_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (isLastPage) stringResource(R.string.action_get_started) else stringResource(R.string.action_next),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

