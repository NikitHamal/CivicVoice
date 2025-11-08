package com.civicvoice.np.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.civicvoice.np.ui.theme.CivicVoiceTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { 3 })

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingSlide(page)
        }
        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Get Started")
        }
    }
}

@Composable
fun OnboardingSlide(page: Int) {
    val text = when (page) {
        0 -> "Share your ideas for change."
        1 -> "Vote and support good suggestions."
        2 -> "See actions taken by your authorities."
        else -> ""
    }
    Text(text = text)
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    CivicVoiceTheme {
        OnboardingScreen(navController = NavController(androidx.compose.ui.platform.LocalContext.current))
    }
}
