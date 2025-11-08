package com.civicvoice.np.ui.screens

import androidx.compose.foundation.clickable
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
import com.civicvoice.np.ui.components.SuggestionCard
import com.civicvoice.np.ui.theme.CivicVoiceTheme

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
    val tabs = listOf("My Suggestions", "My Comments", "Bookmarks")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") }
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
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            modifier = Modifier.size(80.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = user?.name ?: "Guest",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AssistChip(
                                onClick = { },
                                label = {
                                    Text(user?.role?.name?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "Guest")
                                }
                            )
                            if (user?.verified == true) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        if (user != null && user.badges.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                user.badges.take(3).forEach { badge ->
                                    AssistChip(
                                        onClick = { },
                                        label = { Text(badge) },
                                        leadingIcon = {
                                            Icon(
                                                Icons.Default.EmojiEvents,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                TabRow(selectedTabIndex = selectedTab) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title, maxLines = 1) }
                        )
                    }
                }
            }

            when (selectedTab) {
                0 -> {
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
                            SuggestionCard(
                                suggestion = suggestion,
                                onClick = { onSuggestionClick(suggestion.id) },
                                onVote = onVote
                            )
                        }
                    }
                }
                1, 2 -> {
                    item {
                        EmptyState(
                            icon = Icons.Default.Comment,
                            text = "No items yet"
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Card {
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
        }
    }
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
                name = "John Doe",
                email = "john@example.com",
                role = UserRole.CITIZEN,
                verified = true,
                badges = listOf("Active", "Helpful")
            ),
            suggestions = emptyList(),
            isDarkMode = false,
            onToggleDarkMode = {},
            onLogout = {},
            onSuggestionClick = {},
            onVote = { _, _ -> }
        )
    }
}
