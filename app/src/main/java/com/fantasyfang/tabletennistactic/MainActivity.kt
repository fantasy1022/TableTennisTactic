package com.fantasyfang.tabletennistactic

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fantasyfang.tabletennistactic.repository.brush.BrushRepositoryImpl
import com.fantasyfang.tabletennistactic.ui.tactic.TacticScreen
import com.fantasyfang.tabletennistactic.ui.tactic.TacticViewModel
import com.fantasyfang.tabletennistactic.ui.theme.TabletennistacticNewTheme
import com.fantasyfang.tabletennistactic.usecase.tactic.GetTacticUseCase
import com.fantasyfang.tabletennistactic.usecase.tactic.SetTacticUseCase
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


private const val USER_PREFERENCE_NAME = "user_preferences"

//
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCE_NAME
)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TabletennistacticNewTheme {

    }
}