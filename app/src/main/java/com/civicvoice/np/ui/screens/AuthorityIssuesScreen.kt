package com.civicvoice.np.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.civicvoice.np.data.Category
import com.civicvoice.np.data.Status
import com.civicvoice.np.data.Suggestion
import com.civicvoice.np.ui.theme.CivicVoiceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorityIssuesScreen() {
    // Mock data for preview
    val suggestions = listOf(
        Suggestion(id = "1", title = "Improve pedestrian safety on Main St.", category = Category.SAFETY, votes = 120, status = Status.OPEN, authorId = "user1", authorName = "Jane Doe"),
        Suggestion(id = "2", title = "Install more public recycling bins", category = Category.ENVIRONMENT, votes = 95, status = Status.IN_REVIEW, authorId = "user2", authorName = "John Smith")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Issues") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            FilterSection()
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(suggestions) { suggestion ->
                    SuggestionIssueCard(suggestion = suggestion)
                }
            }
        }
    }
}

@Composable
fun SuggestionIssueCard(suggestion: Suggestion) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = suggestion.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Category: ${suggestion.category.name.lowercase().replaceFirstChar { it.titlecase() }}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Upvotes: ${suggestion.votes}", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = { /* Handle details */ }) {
                    Text("Review Details")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { /* Handle status change */ }) {
                    Text("Change Status")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthorityIssuesScreenPreview() {
    CivicVoiceTheme {
        AuthorityIssuesScreen()
    }
}
