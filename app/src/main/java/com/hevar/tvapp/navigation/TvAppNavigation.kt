package com.hevar.tvapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hevar.tvapp.data.FakeChannelRepository
import com.hevar.tvapp.ui.home.HomeScreen
import com.hevar.tvapp.ui.home.HomeViewModel
import com.hevar.tvapp.ui.player.PlayerPlaceholderScreen
import com.hevar.tvapp.ui.settings.SettingsScreen
import com.hevar.tvapp.ui.splash.SplashScreen

// Central navigation graph. Keeps screen transitions simple while the project is still frontend-only.
@Composable
fun TvAppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val repository = FakeChannelRepository()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash,
        modifier = modifier
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
            HomeScreen(
                viewModel = homeViewModel,
                onOpenChannel = { channel -> navController.navigate(Routes.player(channel.id)) },
                onOpenSettings = { navController.navigate(Routes.Settings) }
            )
        }

        composable(Routes.Settings) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }

        composable(
            route = Routes.Player,
            arguments = listOf(navArgument("channelId") { type = NavType.StringType })
        ) { backStackEntry ->
            val channelId = backStackEntry.arguments?.getString("channelId").orEmpty()
            val channel = repository.getChannelById(channelId)
            PlayerPlaceholderScreen(
                channelName = channel?.name ?: "Unknown Channel",
                onBack = { navController.popBackStack() }
            )
        }
    }
}
