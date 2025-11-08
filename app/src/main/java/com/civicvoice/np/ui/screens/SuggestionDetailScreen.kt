package com.civicvoice.np.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestionDetailScreen(
    suggestion: Suggestion,
    comments: List<Comment>,
    relatedSuggestions: List<Suggestion>,
    onBackClick: () -> Unit,
    onVote: (String, Vote) -> Unit,
    onAddComment: (String) -> Unit,
    onSuggestionClick: (String) -> Unit
) {
    var commentText by remember { mutableStateOf("") }
    var aiSummaryExpanded by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Suggestion Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Share, "Share")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.BookmarkBorder, "Bookmark")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CategoryChip(category = suggestion.category)
                    StatusChip(status = suggestion.status)
                }
            }

            item {
                Text(
                    text = suggestion.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = suggestion.authorName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "•",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = formatTimestamp(suggestion.timestamp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (suggestion.aiSummary != null) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize()
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Psychology,
                                        contentDescription = "AI Summary",
                                        tint = MaterialTheme.colorScheme.secondary
                                    )
                                    Text(
                                        "AI Summary",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                IconButton(onClick = { aiSummaryExpanded = !aiSummaryExpanded }) {
                                    Icon(
                                        imageVector = if (aiSummaryExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                        contentDescription = if (aiSummaryExpanded) "Collapse" else "Expand"
                                    )
                                }
                            }
                            if (aiSummaryExpanded) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = suggestion.aiSummary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            // Expert Analysis Card
            item {
                var expertAnalysisExpanded by remember { mutableStateOf(true) }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = "Expert Analysis",
                                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                                Text(
                                    "Expert Analysis",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            IconButton(onClick = { expertAnalysisExpanded = !expertAnalysisExpanded }) {
                                Icon(
                                    imageVector = if (expertAnalysisExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = if (expertAnalysisExpanded) "Collapse" else "Expand"
                                )
                            }
                        }
                        if (expertAnalysisExpanded) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Analysis by: Dr. Ramesh Koirala",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified Expert",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Based on my analysis of similar infrastructure projects in the region, this suggestion shows considerable merit. The proposed improvements align with modern urban planning principles and would significantly enhance pedestrian safety. I recommend prioritizing this initiative in the next fiscal quarter, particularly given the high foot traffic in this area. Implementation should include proper lighting, clear signage, and regular maintenance protocols.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = suggestion.content,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            item {
                Card {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { onVote(suggestion.id, Vote.UP) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (suggestion.userVote == Vote.UP) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant
                                }
                            )
                        ) {
                            Icon(Icons.Default.ThumbUp, "Upvote")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(suggestion.votes.toString())
                        }

                        OutlinedButton(
                            onClick = { onVote(suggestion.id, Vote.DOWN) },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = if (suggestion.userVote == Vote.DOWN) {
                                    MaterialTheme.colorScheme.error
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                }
                            )
                        ) {
                            Icon(Icons.Default.ThumbDown, "Downvote")
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Comments (${comments.size})",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                OutlinedTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Add a comment...") },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (commentText.isNotBlank()) {
                                    onAddComment(commentText)
                                    commentText = ""
                                }
                            },
                            enabled = commentText.isNotBlank()
                        ) {
                            Icon(Icons.Default.Send, "Send")
                        }
                    },
                    shape = MaterialTheme.shapes.medium
                )
            }

            items(comments) { comment ->
                CommentItem(comment = comment)
            }

            if (relatedSuggestions.isNotEmpty()) {
                item {
                    Text(
                        text = "Related Suggestions",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(relatedSuggestions) { related ->
                            RelatedSuggestionCard(
                                suggestion = related,
                                onClick = { onSuggestionClick(related.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = comment.authorName,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                if (comment.verified) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = "Verified",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = formatTimestamp(comment.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = comment.text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelatedSuggestionCard(
    suggestion: Suggestion,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.width(280.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            CategoryChip(category = suggestion.category)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = suggestion.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "↑ ${suggestion.votes}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "💬 ${suggestion.commentCount}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    return when {
        diff < 60000 -> "Just now"
        diff < 3600000 -> "${diff / 60000}m ago"
        diff < 86400000 -> "${diff / 3600000}h ago"
        diff < 604800000 -> "${diff / 86400000}d ago"
        else -> SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(timestamp))
    }
}

@Preview
@Composable
fun SuggestionDetailScreenPreview() {
    CivicVoiceTheme {
        SuggestionDetailScreen(
            suggestion = Suggestion(
                id = "1",
                title = "Install Solar Panels",
                content = "Detailed content here...",
                category = Category.ENVIRONMENT,
                status = Status.UNDER_REVIEW,
                authorId = "1",
                authorName = "Test User",
                votes = 245,
                commentCount = 5,
                aiPriority = true,
                aiSummary = "This is a high-impact environmental initiative..."
            ),
            comments = emptyList(),
            relatedSuggestions = emptyList(),
            onBackClick = {},
            onVote = { _, _ -> },
            onAddComment = {},
            onSuggestionClick = {}
        )
    }
}
