package com.civicvoice.np.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.civicvoice.np.data.*
import com.civicvoice.np.ui.components.CategoryChip
import com.civicvoice.np.ui.components.StatusChip
import com.civicvoice.np.ui.theme.CivicVoiceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssuesScreen(
    suggestions: List<Suggestion>,
    onSuggestionClick: (String) -> Unit,
    onUpdateStatus: (String, Status) -> Unit
) {
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var selectedStatus by remember { mutableStateOf<Status?>(null) }
    var selectedDateFilter by remember { mutableStateOf("Last 30 Days") }

    var showCategoryMenu by remember { mutableStateOf(false) }
    var showStatusMenu by remember { mutableStateOf(false) }
    var showDateMenu by remember { mutableStateOf(false) }

    // Filter suggestions based on selected filters
    val filteredSuggestions = suggestions.filter { suggestion ->
        (selectedCategory == null || suggestion.category == selectedCategory) &&
        (selectedStatus == null || suggestion.status == selectedStatus)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Issues",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
        ) {
            // Filters Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Category Filter
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedButton(
                            onClick = { showCategoryMenu = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Text(
                                text = selectedCategory?.name?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "Category: All",
                                modifier = Modifier.weight(1f)
                            )
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                        DropdownMenu(
                            expanded = showCategoryMenu,
                            onDismissRequest = { showCategoryMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("All") },
                                onClick = {
                                    selectedCategory = null
                                    showCategoryMenu = false
                                }
                            )
                            Category.entries.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(category.name.lowercase().replaceFirstChar { it.uppercase() }) },
                                    onClick = {
                                        selectedCategory = category
                                        showCategoryMenu = false
                                    }
                                )
                            }
                        }
                    }

                    // Status Filter
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedButton(
                            onClick = { showStatusMenu = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Text(
                                text = selectedStatus?.name?.replace("_", " ")?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "Status: All",
                                modifier = Modifier.weight(1f)
                            )
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                        DropdownMenu(
                            expanded = showStatusMenu,
                            onDismissRequest = { showStatusMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("All") },
                                onClick = {
                                    selectedStatus = null
                                    showStatusMenu = false
                                }
                            )
                            Status.entries.forEach { status ->
                                DropdownMenuItem(
                                    text = { Text(status.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }) },
                                    onClick = {
                                        selectedStatus = status
                                        showStatusMenu = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Date Filter (Full Width)
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = { showDateMenu = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Text(
                            text = "Date: $selectedDateFilter",
                            modifier = Modifier.weight(1f)
                        )
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = showDateMenu,
                        onDismissRequest = { showDateMenu = false }
                    ) {
                        listOf("Last 7 Days", "Last 30 Days", "Last 90 Days", "All Time").forEach { dateFilter ->
                            DropdownMenuItem(
                                text = { Text(dateFilter) },
                                onClick = {
                                    selectedDateFilter = dateFilter
                                    showDateMenu = false
                                }
                            )
                        }
                    }
                }
            }

            // Issues List
            if (filteredSuggestions.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "No issues found",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Try adjusting your filters",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredSuggestions) { suggestion ->
                        IssueCard(
                            suggestion = suggestion,
                            onReviewDetailsClick = { onSuggestionClick(suggestion.id) },
                            onChangeStatusClick = { onUpdateStatus(suggestion.id, getNextStatus(suggestion.status)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IssueCard(
    suggestion: Suggestion,
    onReviewDetailsClick: () -> Unit,
    onChangeStatusClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CategoryChip(category = suggestion.category)
                StatusChip(status = suggestion.status)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = suggestion.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Metadata Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Default.ThumbUp,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = suggestion.votes.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Default.Comment,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = suggestion.commentCount.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onReviewDetailsClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Review Details")
                }
                Button(
                    onClick = onChangeStatusClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Change Status")
                }
            }
        }
    }
}

// Helper function to get next status in workflow
fun getNextStatus(currentStatus: Status): Status {
    return when (currentStatus) {
        Status.OPEN -> Status.UNDER_REVIEW
        Status.UNDER_REVIEW -> Status.IMPLEMENTED
        Status.IMPLEMENTED -> Status.OPEN
    }
}

@Preview
@Composable
fun IssuesScreenPreview() {
    CivicVoiceTheme {
        IssuesScreen(
            suggestions = listOf(
                Suggestion(
                    id = "1",
                    title = "Install Solar Panels on Public Buildings",
                    content = "Description here",
                    category = Category.ENVIRONMENT,
                    status = Status.OPEN,
                    authorId = "1",
                    authorName = "Test User",
                    votes = 245,
                    commentCount = 32,
                    aiPriority = true
                ),
                Suggestion(
                    id = "2",
                    title = "Improve Street Lighting",
                    content = "Description here",
                    category = Category.INFRASTRUCTURE,
                    status = Status.UNDER_REVIEW,
                    authorId = "2",
                    authorName = "Another User",
                    votes = 123,
                    commentCount = 15
                )
            ),
            onSuggestionClick = {},
            onUpdateStatus = { _, _ -> }
        )
    }
}
