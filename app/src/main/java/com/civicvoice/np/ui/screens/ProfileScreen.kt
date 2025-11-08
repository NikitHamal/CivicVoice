package com.civicvoice.np.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.civicvoice.np.data.*
import com.civicvoice.np.ui.components.SuggestionCard
import com.civicvoice.np.ui.theme.CivicVoiceTheme
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    user: User?,
    suggestions: List<Suggestion>,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    onLogout: () -> Unit,
    onSuggestionClick: (String) -> Unit,
    onVote: (String, Vote) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("My Suggestions", "Comments", "Bookmarks")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Profile",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle menu */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
        ) {
            // User Profile Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Picture
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFDDB3)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(60.dp),
                            tint = Color(0xFFFFB366).copy(alpha = 0.5f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = user?.name ?: "Jane Doe",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = when (user?.role) {
                            UserRole.CITIZEN -> "Citizen Contributor"
                            UserRole.EXPERT -> "Expert Contributor"
                            UserRole.AUTHORITY -> "Authority Official"
                            else -> "Guest"
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Tabs
            item {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    title,
                                    fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }
            }

            // Tab Content
            when (selectedTab) {
                0 -> {
                    // My Suggestions Tab
                    val userSuggestions = suggestions.filter { it.authorId == user?.id }
                    if (userSuggestions.isEmpty()) {
                        item {
                            EmptyState(
                                icon = Icons.Default.Lightbulb,
                                text = "You haven't created any suggestions yet"
                            )
                        }
                    } else {
                        items(userSuggestions) { suggestion ->
                            ProfileSuggestionItem(
                                suggestion = suggestion,
                                onClick = { onSuggestionClick(suggestion.id) }
                            )
                        }
                    }
                }
                1, 2 -> {
                    item {
                        EmptyState(
                            icon = if (selectedTab == 1) Icons.Default.Comment else Icons.Default.Bookmark,
                            text = "No items yet"
                        )
                    }
                }
            }

            // Settings Section (only show if not on other tabs with content)
            if (selectedTab == 0) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Settings",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            ListItem(
                                headlineContent = { Text("Dark Mode") },
                                leadingContent = {
                                    Icon(Icons.Default.DarkMode, "Dark Mode")
                                },
                                trailingContent = {
                                    Switch(
                                        checked = isDarkMode,
                                        onCheckedChange = { onToggleDarkMode() }
                                    )
                                }
                            )
                            HorizontalDivider()
                            ListItem(
                                headlineContent = { Text("Notifications") },
                                leadingContent = {
                                    Icon(Icons.Default.Notifications, "Notifications")
                                },
                                trailingContent = {
                                    Switch(checked = true, onCheckedChange = { })
                                }
                            )
                            HorizontalDivider()
                            ListItem(
                                headlineContent = { Text("Sign Out") },
                                leadingContent = {
                                    Icon(
                                        Icons.Default.Logout,
                                        "Sign Out",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                },
                                modifier = Modifier.clickableWithoutRipple { onLogout() }
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun ProfileSuggestionItem(
    suggestion: Suggestion,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Status Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(getStatusIconBackgroundColor(suggestion.status)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getStatusIcon(suggestion.status),
                    contentDescription = null,
                    tint = getStatusIconColor(suggestion.status),
                    modifier = Modifier.size(24.dp)
                )
            }

            // Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = suggestion.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Status: ${formatStatus(suggestion.status)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Submitted on ${formatDate(suggestion.timestamp)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Arrow Icon
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "View details",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

fun getStatusIcon(status: Status): ImageVector {
    return when (status) {
        Status.OPEN -> Icons.Default.HourglassEmpty
        Status.UNDER_REVIEW -> Icons.Default.CheckCircle
        Status.IMPLEMENTED -> Icons.Default.CheckCircle
    }
}

fun getStatusIconColor(status: Status): Color {
    return when (status) {
        Status.OPEN -> Color(0xFF6B9BD1)
        Status.UNDER_REVIEW -> Color(0xFF6B9BD1)
        Status.IMPLEMENTED -> Color(0xFF4CAF50)
    }
}

fun getStatusIconBackgroundColor(status: Status): Color {
    return when (status) {
        Status.OPEN -> Color(0xFFE3F2FD)
        Status.UNDER_REVIEW -> Color(0xFFE3F2FD)
        Status.IMPLEMENTED -> Color(0xFFE8F5E9)
    }
}

fun formatStatus(status: Status): String {
    return status.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@Composable
fun EmptyState(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun Modifier.clickableWithoutRipple(onClick: () -> Unit): Modifier {
    return this.then(
        Modifier.clickable(
            indication = null,
            interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
            onClick = onClick
        )
    )
}

@Preview
@Composable
fun ProfileScreenPreview() {
    CivicVoiceTheme {
        ProfileScreen(
            user = User(
                id = "1",
                name = "Jane Doe",
                email = "jane@example.com",
                role = UserRole.CITIZEN,
                verified = true,
                badges = listOf("Active", "Helpful")
            ),
            suggestions = listOf(
                Suggestion(
                    id = "1",
                    title = "Improve pedestrian safety on Main St.",
                    content = "Add more crosswalks",
                    category = Category.SAFETY,
                    status = Status.UNDER_REVIEW,
                    authorId = "1",
                    authorName = "Jane Doe",
                    votes = 42,
                    commentCount = 8,
                    timestamp = System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000
                ),
                Suggestion(
                    id = "2",
                    title = "Install more public recycling bins",
                    content = "Need more recycling options",
                    category = Category.ENVIRONMENT,
                    status = Status.IMPLEMENTED,
                    authorId = "1",
                    authorName = "Jane Doe",
                    votes = 156,
                    commentCount = 23,
                    timestamp = System.currentTimeMillis() - 60L * 24 * 60 * 60 * 1000
                )
            ),
            isDarkMode = false,
            onToggleDarkMode = {},
            onLogout = {},
            onSuggestionClick = {},
            onVote = { _, _ -> }
        )
    }
}
