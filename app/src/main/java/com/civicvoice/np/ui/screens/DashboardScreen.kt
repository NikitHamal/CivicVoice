package com.civicvoice.np.ui.screens

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
fun DashboardScreen(
    suggestions: List<Suggestion>,
    onSuggestionClick: (String) -> Unit,
    onUpdateStatus: (String, Status) -> Unit
) {
    var selectedFilter by remember { mutableStateOf<Status?>(null) }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Authority Dashboard", fontWeight = FontWeight.Bold)
                        Text(
                            "Review community suggestions",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedFilter == null,
                    onClick = { selectedFilter = null },
                    label = { Text("All") }
                )
                FilterChip(
                    selected = selectedFilter == Status.OPEN,
                    onClick = { selectedFilter = Status.OPEN },
                    label = { Text("Open") }
                )
                FilterChip(
                    selected = selectedFilter == Status.UNDER_REVIEW,
                    onClick = { selectedFilter = Status.UNDER_REVIEW },
                    label = { Text("Under Review") }
                )
                FilterChip(
                    selected = selectedFilter == Status.IMPLEMENTED,
                    onClick = { selectedFilter = Status.IMPLEMENTED },
                    label = { Text("Implemented") }
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    StatItem(
                        icon = Icons.Default.Inbox,
                        count = suggestions.count { it.status == Status.OPEN },
                        label = "Open"
                    )
                    StatItem(
                        icon = Icons.Default.HourglassEmpty,
                        count = suggestions.count { it.status == Status.UNDER_REVIEW },
                        label = "Reviewing"
                    )
                    StatItem(
                        icon = Icons.Default.CheckCircle,
                        count = suggestions.count { it.status == Status.IMPLEMENTED },
                        label = "Implemented"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val filteredSuggestions = if (selectedFilter != null) {
                suggestions.filter { it.status == selectedFilter }
            } else {
                suggestions
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredSuggestions) { suggestion ->
                    DashboardSuggestionCard(
                        suggestion = suggestion,
                        onClick = { onSuggestionClick(suggestion.id) },
                        onUpdateStatus = onUpdateStatus
                    )
                }
            }
        }
    }
}

@Composable
fun StatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    count: Int,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun DashboardSuggestionCard(
    suggestion: Suggestion,
    onClick: () -> Unit,
    onUpdateStatus: (String, Status) -> Unit
) {
    var showStatusMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
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

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.ThumbUp,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        suggestion.votes.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Comment,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        suggestion.commentCount.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                if (suggestion.aiPriority) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.LocalFireDepartment,
                            contentDescription = "Priority",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Priority",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { showStatusMenu = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Update Status")
                }

                DropdownMenu(
                    expanded = showStatusMenu,
                    onDismissRequest = { showStatusMenu = false }
                ) {
                    Status.entries.forEach { status ->
                        DropdownMenuItem(
                            text = { Text(status.name.lowercase().replaceFirstChar { it.uppercase() }.replace("_", " ")) },
                            onClick = {
                                onUpdateStatus(suggestion.id, status)
                                showStatusMenu = false
                            },
                            leadingIcon = {
                                Icon(
                                    when (status) {
                                        Status.OPEN -> Icons.Default.Inbox
                                        Status.UNDER_REVIEW -> Icons.Default.HourglassEmpty
                                        Status.IMPLEMENTED -> Icons.Default.CheckCircle
                                    },
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }

                Button(
                    onClick = onClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("View Details")
                }
            }
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    CivicVoiceTheme {
        DashboardScreen(
            suggestions = listOf(
                Suggestion(
                    id = "1",
                    title = "Install Solar Panels",
                    content = "Description here",
                    category = Category.ENVIRONMENT,
                    status = Status.OPEN,
                    authorId = "1",
                    authorName = "Test User",
                    votes = 245,
                    commentCount = 32,
                    aiPriority = true
                )
            ),
            onSuggestionClick = {},
            onUpdateStatus = { _, _ -> }
        )
    }
}
