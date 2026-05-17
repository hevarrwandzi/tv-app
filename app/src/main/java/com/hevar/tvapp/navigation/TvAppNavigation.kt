package com.hevar.tvapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.hevar.tvapp.data.FakeChannelRepository
import com.hevar.tvapp.theme.AppBackground
import com.hevar.tvapp.theme.BorderFocused
import com.hevar.tvapp.theme.SurfaceDark
import com.hevar.tvapp.theme.SurfaceHighlight
import com.hevar.tvapp.ui.home.HomeScreen
import com.hevar.tvapp.ui.home.HomeViewModel
import com.hevar.tvapp.ui.player.PlayerPlaceholderScreen
import com.hevar.tvapp.ui.settings.SettingsScreen
import com.hevar.tvapp.ui.splash.SplashScreen

// Central navigation graph. Uses a phone bottom bar on compact screens and keeps TV screens focus-first.
@Composable
fun TvAppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val repository = FakeChannelRepository()

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val compact = maxWidth < 700.dp
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        NavHost(
            navController = navController,
            startDestination = Routes.Splash,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(Routes.Splash) {
                SplashScreen(
                    onFinished = {
                        navController.navigate(Routes.Home) {
                            popUpTo(Routes.Splash) { inclusive = true }
                        }
                    }
                )
            }

            composable(Routes.Home) {
                val homeViewModel: HomeViewModel = viewModel()
                ScreenShell(
                    compact = compact,
                    currentRoute = currentRoute,
                    onNavigateHome = {
                        navController.navigate(Routes.Home) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(Routes.Home) { saveState = true }
                        }
                    },
                    onNavigateSettings = {
                        navController.navigate(Routes.Settings) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ) {
                    HomeScreen(
                        viewModel = homeViewModel,
                        onOpenChannel = { channel -> navController.navigate(Routes.player(channel.id)) },
                        onOpenSettings = { navController.navigate(Routes.Settings) }
                    )
                }
            }

            composable(Routes.Settings) {
                ScreenShell(
                    compact = compact,
                    currentRoute = currentRoute,
                    onNavigateHome = {
                        navController.navigate(Routes.Home) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(Routes.Home) { saveState = true }
                        }
                    },
                    onNavigateSettings = {
                        navController.navigate(Routes.Settings) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ) {
                    SettingsScreen(
                        onBack = {
                            if (compact) {
                                navController.navigate(Routes.Home) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            } else {
                                navController.popBackStack()
                            }
                        },
                        showBackButton = !compact
                    )
                }
            }

            composable(
                route = Routes.Player,
                arguments = listOf(navArgument("channelId") { type = NavType.StringType })
            ) { backStackEntryForPlayer ->
                val channelId = backStackEntryForPlayer.arguments?.getString("channelId").orEmpty()
                val channel = repository.getChannelById(channelId)
                PlayerPlaceholderScreen(
                    channelName = channel?.name ?: "Unknown Channel",
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
private fun ScreenShell(
    compact: Boolean,
    currentRoute: String?,
    onNavigateHome: () -> Unit,
    onNavigateSettings: () -> Unit,
    content: @Composable () -> Unit
) {
    if (!compact || (currentRoute != Routes.Home && currentRoute != Routes.Settings)) {
        content()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        Box(modifier = Modifier.weight(1f)) {
            content()
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceDark)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BottomNavItem(
                label = "Home",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(20.dp)
                    )
                },
                selected = currentRoute == Routes.Home,
                onClick = onNavigateHome,
                modifier = Modifier.weight(1f)
            )
            BottomNavItem(
                label = "Settings",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(20.dp)
                    )
                },
                selected = currentRoute == Routes.Settings,
                onClick = onNavigateSettings,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    label: String,
    icon: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = if (selected) SurfaceHighlight else AppBackground,
                shape = RoundedCornerShape(18.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .background(
                        color = if (selected) BorderFocused.copy(alpha = 0.18f) else SurfaceHighlight.copy(alpha = 0.55f),
                        shape = CircleShape
                    )
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                color = if (selected) BorderFocused else MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (selected) {
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .background(BorderFocused, RoundedCornerShape(50))
                        .fillMaxWidth(0.20f)
                        .height(4.dp)
                )
            }
        }
    }
}
