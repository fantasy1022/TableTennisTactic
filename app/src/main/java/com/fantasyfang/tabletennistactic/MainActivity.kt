package com.fantasyfang.tabletennistactic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fantasyfang.tabletennistactic.ui.setting.SettingScreen
import com.fantasyfang.tabletennistactic.ui.tactic.TacticScreen
import com.fantasyfang.tabletennistactic.ui.theme.TableTennisTacticNewTheme
import com.fantasyfang.tabletennistactic.util.NavigationRoute

class MainActivity : ComponentActivity() {

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TableTennisTacticNewTheme { colorScheme ->
                AppNavigation(colorScheme)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun AppNavigation(colorScheme: ColorScheme) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationRoute.TACTIC.route) {
        composable(NavigationRoute.TACTIC.route) { TacticScreen(navController) }
        composable(NavigationRoute.SETTING.route) { SettingScreen(colorScheme) }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TableTennisTacticNewTheme {

    }
}