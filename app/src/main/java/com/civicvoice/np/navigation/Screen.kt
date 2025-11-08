package com.civicvoice.np.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Home : Screen("home")
    object Create : Screen("create")
    object Dashboard : Screen("dashboard")
    object Profile : Screen("profile")
object AuthorityMain : Screen("authority_main")
    object SuggestionDetail : Screen("suggestion_detail/{suggestionId}") {
        fun createRoute(suggestionId: String) = "suggestion_detail/$suggestionId"
    }
}
