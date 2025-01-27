package com.example.softcom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.softcom.data.DatabaseInstance
import com.example.softcom.ui.cart.CartScreen
import com.example.softcom.ui.home.HomeScreen
import com.example.softcom.ui.home.HomeViewModel
import com.example.softcom.ui.product.ProductDetailsScreen
import com.example.softcom.ui.theme.LojaVirtualTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)

        val db = DatabaseInstance.getDatabase(this)
        val produtoDao = db.produtoDao()

        val homeViewModel: HomeViewModel by viewModels()

        setContent {
            LojaVirtualTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    MainNavGraph(navController = navController, homeViewModel = homeViewModel)
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainNavGraph(navController: NavHostController, homeViewModel: HomeViewModel) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController, viewModel = homeViewModel)
        }

        composable(
            "produtoDetalhes/{produtoId}",
            arguments = listOf(navArgument("produtoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val produtoId = backStackEntry.arguments?.getInt("produtoId")
            val produto = homeViewModel.produtos.value.find { it.id == produtoId }

            if (produto != null) {
                ProductDetailsScreen(navController = navController, produto = produto, viewModel = homeViewModel)
            } else {
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            }
        }

        composable("cart") {
            CartScreen(
                navController = navController,
                viewModel = homeViewModel
            )
        }
    }
}
