package com.example.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.PrivacyPolicyScreen
import com.example.ui.screens.ServerListScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.TermsScreen
import com.example.viewmodel.VpnViewModel

object NavRoutes {
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
    const val SERVERS = "servers"
    const val SETTINGS = "settings"
    const val ABOUT = "about"
    const val PRIVACY_POLICY = "privacy_policy"
    const val TERMS = "terms"
}

@Composable
fun VpnNavGraph(
    viewModel: VpnViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val settings by viewModel.settings.collectAsState()
    val startDestination = if (settings.hasCompletedOnboarding) NavRoutes.HOME else NavRoutes.ONBOARDING

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { fadeIn(animationSpec = tween(250)) },
        exitTransition = { fadeOut(animationSpec = tween(250)) }
    ) {
        composable(NavRoutes.ONBOARDING) {
            OnboardingScreen(
                onFinishOnboarding = {
                    viewModel.completeOnboarding()
                    navController.navigate(NavRoutes.HOME) {
                        popUpTo(NavRoutes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        composable(NavRoutes.HOME) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToServers = {
                    navController.navigate(NavRoutes.SERVERS)
                },
                onNavigateToSettings = {
                    navController.navigate(NavRoutes.SETTINGS)
                }
            )
        }

        composable(NavRoutes.SERVERS) {
            ServerListScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavRoutes.SETTINGS) {
            SettingsScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onNavigateToAbout = {
                    navController.navigate(NavRoutes.ABOUT)
                },
                onNavigateToPrivacy = {
                    navController.navigate(NavRoutes.PRIVACY_POLICY)
                },
                onNavigateToTerms = {
                    navController.navigate(NavRoutes.TERMS)
                }
            )
        }

        composable(NavRoutes.ABOUT) {
            AboutScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavRoutes.PRIVACY_POLICY) {
            PrivacyPolicyScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavRoutes.TERMS) {
            TermsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
