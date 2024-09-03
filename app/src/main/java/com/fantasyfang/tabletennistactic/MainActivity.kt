package com.fantasyfang.tabletennistactic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fantasyfang.tabletennistactic.ui.tactic.TacticScreen
import com.fantasyfang.tabletennistactic.ui.theme.TabletennistacticNewTheme
import com.fantasyfang.tabletennistactic.util.NavigationRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TabletennistacticNewTheme {
                AppNavigation()
            }
        }
    }
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationRoute.TACTIC.route) {
        composable(NavigationRoute.TACTIC.route) { TacticScreen(navController) }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TabletennistacticNewTheme {

    }
}