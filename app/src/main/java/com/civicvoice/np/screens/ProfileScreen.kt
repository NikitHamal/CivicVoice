package com.civicvoice.np.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.civicvoice.np.ui.theme.CivicVoiceTheme

@Composable
fun ProfileScreen(navController: NavController) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("My Suggestions", "My Comments", "Bookmarks")

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Avatar",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "John Doe", style = MaterialTheme.typography.titleLarge)
                Text(text = "Citizen", style = MaterialTheme.typography.bodyLarge)
            }
        }
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    text = { Text(text = title) }
                )
            }
        }
        // TODO: Display content based on selected tab
        Spacer(modifier = Modifier.weight(1f))
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Settings", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(text = "Dark mode")
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = false, onCheckedChange = { /* Handle dark mode */ })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(text = "Notifications")
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = true, onCheckedChange = { /* Handle notifications */ })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /* Handle sign out */ }) {
                Text(text = "Sign Out")
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    CivicVoiceTheme {
        ProfileScreen(navController = NavController(androidx.compose.ui.platform.LocalContext.current))
    }
}
