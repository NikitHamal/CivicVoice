package com.civicvoice.np.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.civicvoice.np.composables.SuggestionCard
import com.civicvoice.np.ui.theme.CivicVoiceTheme

@Composable
fun AuthorityDashboardScreen(navController: NavController) {
    var selectedFilter by remember { mutableStateOf("Open") }
    val filters = listOf("Open", "Under Review", "Implemented")

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(16.dp)) {
            filters.forEach { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(text = filter) }
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        // TODO: Add fake charts
        LazyColumn {
            items(5) {
                SuggestionCard(
                    title = "Suggestion for review",
                    summary = "This is a suggestion that needs to be reviewed by an authority.",
                    category = "Other",
                    upvotes = 50,
                    comments = 10,
                    status = selectedFilter,
                    isAiPriority = false
                )
            }
        }
    }
}

@Preview
@Composable
fun AuthorityDashboardScreenPreview() {
    CivicVoiceTheme {
        AuthorityDashboardScreen(navController = NavController(androidx.compose.ui.platform.LocalContext.current))
    }
}
