package com.civicvoice.np.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.civicvoice.np.ui.theme.CivicVoiceTheme

@Composable
fun LoginScreen(navController: NavController) {
    var role by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { /* Handle Google Sign-in */ }) {
            Text(text = "Sign in with Google")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("home") }) {
            Text(text = "Continue as Guest")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Select your role:")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            role = "Citizen"
            navController.navigate("home")
        }) {
            Text(text = "Citizen")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            role = "Expert"
            navController.navigate("home")
        }) {
            Text(text = "Expert")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            role = "Authority"
            navController.navigate("home")
        }) {
            Text(text = "Authority")
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    CivicVoiceTheme {
        LoginScreen(navController = NavController(androidx.compose.ui.platform.LocalContext.current))
    }
}
