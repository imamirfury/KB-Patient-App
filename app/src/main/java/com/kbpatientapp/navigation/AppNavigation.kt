package com.kbpatientapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kbpatientapp.ui.screens.HomeScreen
import com.kbpatientapp.ui.screens.RegistrationScreen
import com.kbpatientapp.ui.screens.SummaryScreen
import com.kbpatientapp.ui.screens.VisitDetailsScreen
import com.kbpatientapp.ui.screens.VisitsScreen

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home")
    object RegistrationScreen : Screen("registration")
    object VisitsScreen : Screen("visits/{patientId}") {
        fun createRoute(patientId: Long) = "visits/$patientId"
    }

    object VisitDetailsScreen : Screen("visitDetails/{patientId}") {
        fun createRoute(patientId: Long) = "visitDetails/$patientId"
    }

    object SummeryScreen : Screen("summery/{patientId}/{visitId}") {
        fun createRoute(patientId: Long, visitId: Long) = "summery/$patientId/$visitId"
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController)
        }

        composable(Screen.RegistrationScreen.route) {
            RegistrationScreen(navController)
        }

        composable(route = Screen.VisitsScreen.route, arguments = listOf(navArgument("patientId") {
            type =
                NavType.LongType
        })) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getLong("patientId") ?: -1L
            VisitsScreen(navController, patientId)
        }

        composable(
            route = Screen.VisitDetailsScreen.route,
            arguments = listOf(navArgument("patientId") { type = NavType.LongType })
        ) { backStateEntry ->
            val patientId = backStateEntry.arguments?.getLong("patientId") ?: -1L
            VisitDetailsScreen(navController, patientId)
        }

        composable(
            route = Screen.SummeryScreen.route, arguments = listOf(navArgument("patientId") {
                type =
                    NavType.LongType
            }, navArgument("visitId") { type = NavType.LongType })
        ) { backstackEntry ->
            val patientId = backstackEntry.arguments?.getLong("patientId") ?: -1L
            val visitId = backstackEntry.arguments?.getLong("visitId") ?: -1L
            SummaryScreen(navController, patientId, visitId)
        }
    }
}