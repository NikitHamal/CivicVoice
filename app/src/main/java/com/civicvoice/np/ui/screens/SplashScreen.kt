package com.civicvoice.np.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.civicvoice.np.ui.theme.CivicVoiceTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToNext: () -> Unit
) {
    val scale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        delay(1500)
        onNavigateToNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Campaign,
                contentDescription = "CivicVoice Logo",
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale.value),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "CivicVoice",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.scale(scale.value)
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    CivicVoiceTheme {
        SplashScreen(onNavigateToNext = {})
    }
}
