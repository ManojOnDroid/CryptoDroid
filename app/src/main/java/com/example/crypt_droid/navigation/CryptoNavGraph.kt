package com.example.crypt_droid.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.crypt_droid.screen.CryptoDetailScreen
import com.example.crypt_droid.viewmodel.CryptoViewModel
import com.example.crypt_droid.screen.CryptoScreen

@Composable
fun CryptoNavGraph(
    viewModel: CryptoViewModel = viewModel()
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppDestinations.CryptoListRoute,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween (700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween (700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween (700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween (700)
            )
        }
    ) {
        composable<AppDestinations.CryptoListRoute> {
            CryptoScreen(
                viewModel = viewModel,
                onCryptoClick = { symbol ->
                    navController.navigate(AppDestinations.CryptoDetailRoute(symbol))
                }
            )
        }
        composable<AppDestinations.CryptoDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<AppDestinations.CryptoDetailRoute>()
            CryptoDetailScreen(
                symbol = route.symbol,
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}