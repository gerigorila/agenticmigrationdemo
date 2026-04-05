package com.gerigorila.agenticmigrationdemo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gerigorila.agenticmigrationdemo.presentation.home.HomeScreen
import com.gerigorila.agenticmigrationdemo.presentation.productdetail.ProductDetailScreen
import com.gerigorila.agenticmigrationdemo.presentation.productlist.ProductListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onStartClick = { navController.navigate("products") })
        }
        composable("products") {
            ProductListScreen(onProductClick = { productId ->
                navController.navigate("product/$productId")
            })
        }
        composable(
            route = "product/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            ProductDetailScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
