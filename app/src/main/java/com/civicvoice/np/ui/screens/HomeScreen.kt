package com.civicvoice.np.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.civicvoice.np.data.Category
import com.civicvoice.np.data.Status
import com.civicvoice.np.data.Suggestion
import com.civicvoice.np.data.Vote
import com.civicvoice.np.ui.components.SuggestionCard
import com.civicvoice.np.ui.theme.CivicVoiceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    suggestions: List<Suggestion>,
    onSuggestionClick: (String) -> Unit,
    onVote: (String, Vote) -> Unit,
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit,
    onCreateClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("All", "Trending", "Nearby", "Category")
    var showFilterDialog by remember { mutableStateOf(false) }
    var sortBy by remember { mutableStateOf("Trending") }
    var statusFilters by remember { mutableStateOf(setOf<Status>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Campaign,
                            contentDescription = "Logo",
                            modifier = Modifier.size(28.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "CivicVoice",
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(Icons.Default.FilterList, "Filter & Sort")
                    }
                    IconButton(onClick = onSearchClick) {
                        Icon(Icons.Default.Search, "Search")
                    }
                    IconButton(onClick = onProfileClick) {
                        Icon(Icons.Default.AccountCircle, "Profile")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, "Create Suggestion")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 16.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            var filteredSuggestions = when (selectedTab) {
                1 -> suggestions.sortedByDescending { it.votes }
                2 -> suggestions.filter { it.location != null }
                else -> suggestions
            }

            // Apply status filters
            if (statusFilters.isNotEmpty()) {
                filteredSuggestions = filteredSuggestions.filter { it.status in statusFilters }
            }

            // Apply sorting
            filteredSuggestions = when (sortBy) {
                "Most Upvoted" -> filteredSuggestions.sortedByDescending { it.votes }
                "Most Recent" -> filteredSuggestions.sortedByDescending { it.timestamp }
                else -> filteredSuggestions // Trending (default)
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredSuggestions) { suggestion ->
                    SuggestionCard(
                        suggestion = suggestion,
                        onClick = { onSuggestionClick(suggestion.id) },
                        onVote = onVote
                    )
                }
            }
        }
    }

    // Filter & Sort Dialog
    if (showFilterDialog) {
        FilterSortDialog(
            currentSortBy = sortBy,
            currentStatusFilters = statusFilters,
            onDismiss = { showFilterDialog = false },
            onApply = { newSortBy, newStatusFilters ->
                sortBy = newSortBy
                statusFilters = newStatusFilters
                showFilterDialog = false
            }
        )
    }
}

@Composable
fun FilterSortDialog(
    currentSortBy: String,
    currentStatusFilters: Set<Status>,
    onDismiss: () -> Unit,
    onApply: (String, Set<Status>) -> Unit
) {
    var selectedSortBy by remember { mutableStateOf(currentSortBy) }
    var selectedStatusFilters by remember { mutableStateOf(currentStatusFilters) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Filter & Sort",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Sort Section
                Text(
                    text = "Sort By:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Trending", "Most Upvoted", "Most Recent").forEach { option ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedSortBy == option,
                                onClick = { selectedSortBy = option }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(option, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Filter Section
                Text(
                    text = "Filter by Status:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(
                        Status.OPEN to "Open",
                        Status.UNDER_REVIEW to "In Review",
                        Status.IMPLEMENTED to "Implemented"
                    ).forEach { (status, label) ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = status in selectedStatusFilters,
                                onCheckedChange = { checked ->
                                    selectedStatusFilters = if (checked) {
                                        selectedStatusFilters + status
                                    } else {
                                        selectedStatusFilters - status
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(label, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onApply(selectedSortBy, selectedStatusFilters) }
            ) {
                Text("Apply Filters")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    CivicVoiceTheme {
        HomeScreen(
            suggestions = listOf(
                Suggestion(
                    id = "1",
                    title = "Install Solar Panels",
                    content = "We should install solar panels on government buildings",
                    category = Category.ENVIRONMENT,
                    status = Status.OPEN,
                    authorId = "1",
                    authorName = "Test User",
                    votes = 42,
                    commentCount = 8,
                    aiPriority = true
                )
            ),
            onSuggestionClick = {},
            onVote = { _, _ -> },
            onSearchClick = {},
            onProfileClick = {},
            onCreateClick = {}
        )
    }
}
