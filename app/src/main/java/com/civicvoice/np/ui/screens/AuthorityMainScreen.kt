package com.civicvoice.np.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

sealed class AuthorityScreen(val route: String, val label: String, val icon: ImageVector) {
    object Dashboard : AuthorityScreen("dashboard", "Dashboard", Icons.Default.Dashboard)
    object Issues : AuthorityScreen("issues", "Issues", Icons.Default.List)
    object Reports : AuthorityScreen("reports", "Reports", Icons.Default.Assessment)
    object Profile : AuthorityScreen("profile", "Profile", Icons.Default.Person)
}

val authorityScreens = listOf(
    AuthorityScreen.Dashboard,
    AuthorityScreen.Issues,
    AuthorityScreen.Reports,
    AuthorityScreen.Profile,
)

@Composable
fun AuthorityMainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                authorityScreens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = AuthorityScreen.Dashboard.route, Modifier.padding(innerPadding)) {
            composable(AuthorityScreen.Dashboard.route) { AuthorityDashboardScreen() }
            composable(AuthorityScreen.Issues.route) { AuthorityIssuesScreen() }
            composable(AuthorityScreen.Reports.route) { AuthorityReportsScreen() }
            composable(AuthorityScreen.Profile.route) { AuthorityProfileScreen() }
        }
    }
}
