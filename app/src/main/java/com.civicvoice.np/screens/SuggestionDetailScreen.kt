package com.civicvoice.np.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.civicvoice.np.composables.SuggestionCard
import com.civicvoice.np.ui.theme.CivicVoiceTheme

@Composable
fun SuggestionDetailScreen(navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Improve public transportation", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "The current public transportation system is inadequate for the needs of the city. We need to invest in new buses and routes. This will help to reduce traffic congestion and improve air quality.",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "AI Summary", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "The suggestion is to improve the public transportation system by investing in new buses and routes.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    IconButton(onClick = { /* Handle upvote */ }) {
                        Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = "Upvote")
                    }
                    IconButton(onClick = { /* Handle downvote */ }) {
                        Icon(imageVector = Icons.Default.ArrowDownward, contentDescription = "Downvote")
                    }
                }
            }
        }
        item {
            Text(
                text = "Comments",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
        // TODO: Add comment section
        item {
            Text(
                text = "Related Suggestions",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(3) {
            SuggestionCard(
                title = "Related Suggestion",
                summary = "This is a related suggestion.",
                category = "Transportation",
                upvotes = 10,
                comments = 2,
                status = "Open",
                isAiPriority = false
            )
        }
    }
}

@Preview
@Composable
fun SuggestionDetailScreenPreview() {
    CivicVoiceTheme {
        SuggestionDetailScreen(navController = NavController(androidx.compose.ui.platform.LocalContext.current))
    }
}
