package com.civicvoice.np.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.civicvoice.np.R
import com.civicvoice.np.data.*
import com.civicvoice.np.ui.theme.CivicVoiceTheme
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    user: User?,
    suggestions: List<Suggestion>,
    onSuggestionClick: (String) -> Unit,
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("My Suggestions", "Comments", "Bookmarks")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back press */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle menu click */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                // Profile Picture
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = user?.name ?: "Jane Doe",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Citizen Contributor",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                TabRow(
                    selectedTabIndex = selectedTab,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) }
                        )
                    }
                }
            }

            when (selectedTab) {
                0 -> {
                    val userSuggestions = suggestions.filter { it.authorId == user?.id }
                    if (userSuggestions.isEmpty()) {
                        item {
                           Text("No suggestions yet.", modifier = Modifier.padding(16.dp))
                        }
                    } else {
                        items(userSuggestions) { suggestion ->
                            MySuggestionListItem(suggestion = suggestion, onClick = { onSuggestionClick(suggestion.id) })
                        }
                    }
                }
                1, 2 -> {
                     item {
                           Text("Not yet implemented.", modifier = Modifier.padding(16.dp))
                        }
                }
            }
        }
    }
}

@Composable
fun MySuggestionListItem(suggestion: Suggestion, onClick: () -> Unit) {
    val statusIcon = when (suggestion.status) {
        Status.IN_REVIEW -> Icons.Default.Person
        Status.IMPLEMENTED -> Icons.Default.CheckCircle
        Status.REJECTED -> Icons.Default.Cancel
        else -> Icons.Default.Lightbulb
    }

    val dateFormatter = remember {
        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = statusIcon,
                contentDescription = "Status Icon",
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = suggestion.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Status: ${suggestion.status.name.replace('_', ' ').lowercase().replaceFirstChar { it.titlecase() }}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Submitted on ${dateFormatter.format(suggestion.timestamp)}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Details")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    CivicVoiceTheme {
        ProfileScreen(
            user = User(
                id = "1",
                name = "Jane Doe",
                email = "jane.doe@example.com",
                role = UserRole.CITIZEN
            ),
            suggestions = listOf(
                Suggestion(id = "1", title = "Improve pedestrian safety on Main St.", content = "", category = Category.SAFETY, authorName = "Jane Doe", authorId = "1", status = Status.IN_REVIEW, timestamp = Date().time),
                Suggestion(id = "2", title = "Install more public recycling bins", content = "", category = Category.ENVIRONMENT, authorName = "Jane Doe", authorId = "1", status = Status.IMPLEMENTED, timestamp = Date().time),
                Suggestion(id = "3", title = "Create a new community garden", content = "", category = Category.ENVIRONMENT, authorName = "Jane Doe", authorId = "1", status = Status.OPEN, timestamp = Date().time),
                 Suggestion(id = "4", title = "Add speed bumps to Elm Street", content = "", category = Category.TRANSPORTATION, authorName = "Jane Doe", authorId = "1", status = Status.REJECTED, timestamp = Date().time)
            ),
            onSuggestionClick = {}
        )
    }
}
