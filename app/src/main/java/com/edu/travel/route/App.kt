package com.edu.travel.route

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.edu.travel.ui.presenration.dashboard.DashboardScreen
import com.edu.travel.ui.presenration.detail.DetailScreen
import com.edu.travel.ui.presenration.splash.SplashScreen
import com.edu.travel.ui.presenration.ticket.TicketDetailScreen

@Composable
fun App(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.Splash
    ){
        composable<Route.Splash> {
            SplashScreen(
                navigateToRoute = {
                    navController.navigate(it) {
                        popUpTo<Route.Splash> { inclusive = true}
                        launchSingleTop = true
                    }
                }
            )}
        composable<Route.Dashboard> {
            DashboardScreen(
                navigateToRoute = {
                    navController.navigate(it){}
                }
            )}
        composable<Route.Detail> {
            DetailScreen(
                routeParams = it.toRoute(),
                upPress = {
                    navController.navigateUp()
                },
                navigateToRoute = { route ->
                    navController.navigate(route)
                }
            )
        }
        composable<Route.Ticket> {
            TicketDetailScreen(
                routeParams = it.toRoute(),
                upPress = {
                    navController.navigateUp()
                },
            )
        }
    }
}
