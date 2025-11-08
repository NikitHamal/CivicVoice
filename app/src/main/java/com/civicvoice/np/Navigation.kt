package com.civicvoice.np

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.civicvoice.np.screens.*

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "splash", modifier = modifier) {
        composable("splash") { SplashScreen(navController) }
        composable("onboarding") { OnboardingScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("suggestion_detail") { SuggestionDetailScreen(navController) }
        composable("create_suggestion") { CreateSuggestionScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("authority_dashboard") { AuthorityDashboardScreen(navController) }
    }
}
