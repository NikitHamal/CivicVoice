package com.civicvoice.np.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.civicvoice.np.data.UserRole
import com.civicvoice.np.ui.screens.*
import com.civicvoice.np.viewmodel.MainViewModel

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem(Screen.Home.route, Icons.Default.Home, "Home")
    object Create : BottomNavItem(Screen.Create.route, Icons.Default.Add, "Create")
    object Dashboard : BottomNavItem(Screen.Dashboard.route, Icons.Default.Dashboard, "Dashboard")
    object Issues : BottomNavItem(Screen.Issues.route, Icons.Default.List, "Issues")
    object Reports : BottomNavItem(Screen.Reports.route, Icons.Default.BarChart, "Reports")
    object Profile : BottomNavItem(Screen.Profile.route, Icons.Default.Person, "Profile")
}

@Composable
fun AppNavigation(
    viewModel: MainViewModel = viewModel()
) {
    val navController = rememberNavController()
    val currentUser by viewModel.currentUser.collectAsState()
    val showOnboarding by viewModel.showOnboarding.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val suggestions by viewModel.suggestions.collectAsState()

    val startDestination = when {
        showOnboarding -> Screen.Onboarding.route
        currentUser == null -> Screen.Login.route
        else -> Screen.Home.route
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToNext = {
                    navController.navigate(startDestination) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onComplete = {
                    viewModel.completeOnboarding()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLogin = { name, role ->
                    viewModel.login(name, role)
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            MainScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.SuggestionDetail.route,
            arguments = listOf(navArgument("suggestionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val suggestionId = backStackEntry.arguments?.getString("suggestionId")
            val suggestion = suggestions.find { it.id == suggestionId }

            if (suggestion != null) {
                val comments = viewModel.getCommentsForSuggestion(suggestionId!!)
                val relatedSuggestions = suggestions
                    .filter { it.category == suggestion.category && it.id != suggestionId }
                    .take(3)

                SuggestionDetailScreen(
                    suggestion = suggestion,
                    comments = comments,
                    relatedSuggestions = relatedSuggestions,
                    onBackClick = { navController.popBackStack() },
                    onVote = { id, vote -> viewModel.voteSuggestion(id, vote) },
                    onAddComment = { text -> viewModel.addComment(suggestionId, text) },
                    onSuggestionClick = { id ->
                        navController.navigate(Screen.SuggestionDetail.createRoute(id))
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val mainNavController = rememberNavController()
    val currentUser by viewModel.currentUser.collectAsState()
    val suggestions by viewModel.suggestions.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()

    val bottomNavItems = if (currentUser?.role == UserRole.AUTHORITY) {
        listOf(BottomNavItem.Dashboard, BottomNavItem.Issues, BottomNavItem.Reports, BottomNavItem.Profile)
    } else {
        listOf(BottomNavItem.Home, BottomNavItem.Create, BottomNavItem.Profile)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            mainNavController.navigate(item.route) {
                                popUpTo(mainNavController.graph.findStartDestination().id) {
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
        NavHost(
            navController = mainNavController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    suggestions = suggestions,
                    onSuggestionClick = { id ->
                        navController.navigate(Screen.SuggestionDetail.createRoute(id))
                    },
                    onVote = { id, vote -> viewModel.voteSuggestion(id, vote) },
                    onSearchClick = { },
                    onProfileClick = {
                        mainNavController.navigate(BottomNavItem.Profile.route)
                    },
                    onCreateClick = {
                        mainNavController.navigate(BottomNavItem.Create.route)
                    }
                )
            }

            composable(BottomNavItem.Create.route) {
                CreateSuggestionScreen(
                    onBackClick = { mainNavController.popBackStack() },
                    onSubmit = { title, content, category, isAnonymous ->
                        viewModel.addSuggestion(title, content, category, isAnonymous)
                    },
                    onSuccess = {
                        mainNavController.navigate(BottomNavItem.Home.route) {
                            popUpTo(BottomNavItem.Home.route) { inclusive = true }
                        }
                    }
                )
            }

            if (currentUser?.role == UserRole.AUTHORITY) {
                composable(BottomNavItem.Dashboard.route) {
                    DashboardScreen(
                        suggestions = suggestions,
                        onSuggestionClick = { id ->
                            navController.navigate(Screen.SuggestionDetail.createRoute(id))
                        },
                        onUpdateStatus = { id, status ->
                            viewModel.updateSuggestionStatus(id, status)
                        }
                    )
                }

                composable(BottomNavItem.Issues.route) {
                    IssuesScreen(
                        suggestions = suggestions,
                        onSuggestionClick = { id ->
                            navController.navigate(Screen.SuggestionDetail.createRoute(id))
                        },
                        onUpdateStatus = { id, status ->
                            viewModel.updateSuggestionStatus(id, status)
                        }
                    )
                }

                composable(BottomNavItem.Reports.route) {
                    ReportsScreen()
                }
            }

            composable(BottomNavItem.Profile.route) {
                ProfileScreen(
                    user = currentUser,
                    suggestions = suggestions,
                    isDarkMode = isDarkMode,
                    onToggleDarkMode = { viewModel.toggleDarkMode() },
                    onLogout = {
                        viewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    },
                    onSuggestionClick = { id ->
                        navController.navigate(Screen.SuggestionDetail.createRoute(id))
                    },
                    onVote = { id, vote -> viewModel.voteSuggestion(id, vote) }
                )
            }
        }
    }
}
