package com.example.radiofrancestation.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.radiofrancestation.presentation.ui.StationListScreen
import com.example.radiofrancestation.presentation.ui.StationShowsScreen

const val PARAM_STATION_ID = "stationId"

enum class Routes(val title: String) {
    STATIONS(title = "stationList"),
    SHOWS(title = "showList/{$PARAM_STATION_ID}")
}

fun Routes.createRoute(
    stationId: String,
): String {
    return when (this) {
        Routes.SHOWS -> "showList/$stationId"
        Routes.STATIONS -> this.title
    }
}

@Composable
fun Router(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Routes.STATIONS.title) {
        composable(Routes.STATIONS.title) {
            StationListScreen(modifier = modifier, navController = navController)
        }
        composable(route = Routes.SHOWS.title,
            arguments = listOf(
                navArgument(PARAM_STATION_ID) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(PARAM_STATION_ID)
            StationShowsScreen()
        }
    }
}