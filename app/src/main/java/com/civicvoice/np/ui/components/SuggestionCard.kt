package com.civicvoice.np.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.civicvoice.np.data.Category
import com.civicvoice.np.data.Status
import com.civicvoice.np.data.Suggestion
import com.civicvoice.np.data.Vote
import com.civicvoice.np.ui.theme.CivicVoiceTheme

@Composable
fun SuggestionCard(
    suggestion: Suggestion,
    onClick: () -> Unit,
    onVote: (String, Vote) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CategoryChip(category = suggestion.category)
                    if (suggestion.aiPriority) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = "AI Priority",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                StatusChip(status = suggestion.status)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = suggestion.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = suggestion.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { onVote(suggestion.id, Vote.UP) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowUpward,
                            contentDescription = "Upvote",
                            tint = if (suggestion.userVote == Vote.UP) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Text(
                        text = suggestion.votes.toString(),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    IconButton(
                        onClick = { onVote(suggestion.id, Vote.DOWN) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDownward,
                            contentDescription = "Downvote",
                            tint = if (suggestion.userVote == Vote.DOWN) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Comment,
                            contentDescription = "Comments",
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = suggestion.commentCount.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (suggestion.location != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location",
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = suggestion.location,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryChip(category: Category) {
    val categoryName = category.name.lowercase().replaceFirstChar { it.uppercase() }
    AssistChip(
        onClick = { },
        label = {
            Text(
                text = categoryName,
                style = MaterialTheme.typography.labelSmall
            )
        },
        leadingIcon = {
            Icon(
                imageVector = when (category) {
                    Category.INFRASTRUCTURE -> Icons.Default.Construction
                    Category.EDUCATION -> Icons.Default.School
                    Category.HEALTH -> Icons.Default.LocalHospital
                    Category.ENVIRONMENT -> Icons.Default.Park
                    Category.TRANSPORTATION -> Icons.Default.DirectionsBus
                    Category.SAFETY -> Icons.Default.Security
                    Category.OTHER -> Icons.Default.Category
                },
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    )
}

@Composable
fun StatusChip(status: Status) {
    val (text, color) = when (status) {
        Status.OPEN -> "Open" to MaterialTheme.colorScheme.primary
        Status.UNDER_REVIEW -> "Under Review" to MaterialTheme.colorScheme.tertiary
        Status.IMPLEMENTED -> "Implemented" to MaterialTheme.colorScheme.secondary
    }

    Surface(
        color = color.copy(alpha = 0.12f),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
fun SuggestionCardPreview() {
    CivicVoiceTheme {
        SuggestionCard(
            suggestion = Suggestion(
                id = "1",
                title = "Install Solar Panels on Government Buildings",
                content = "We should install solar panels on all government buildings to reduce costs",
                category = Category.ENVIRONMENT,
                status = Status.UNDER_REVIEW,
                authorId = "1",
                authorName = "Test User",
                votes = 245,
                commentCount = 32,
                aiPriority = true,
                location = "Kathmandu"
            ),
            onClick = {},
            onVote = { _, _ -> }
        )
    }
}
