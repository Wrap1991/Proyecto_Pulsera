package com.miempresa.proyecto_pulsera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Principal()
        }
    }
}

@Composable
fun Principal() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "pantalla1") {
        composable(route = "pantalla1") { Screen1(navController) }
        composable(route = "pantalla2") { Screen2(navController) }
        composable(route = "pantalla3/{texto}") { backStackEntry ->
            val texto = backStackEntry.arguments?.getString("texto")
            Screen3(navController, texto ?: "")
        }
        composable(route = "pantalla4") { Screen4(navController) }
        composable(route = "pantalla5") { Screen5(navController) }
        composable(route = "pantalla6") { Screen6(navController) }
        composable(route = "pantalla7") { Screen7(navController) }
        composable(route = "pantalla8") { Screen8(navController) }
        composable(route = "pantalla9") { Screen9(navController) }
        composable(route = "pantalla10") { Screen10(navController) }
        composable(route = "pantalla11") { Screen11(navController) }
    }
}

@Preview(showBackground = true)
@Composable
fun Vista() {
    Principal()
}