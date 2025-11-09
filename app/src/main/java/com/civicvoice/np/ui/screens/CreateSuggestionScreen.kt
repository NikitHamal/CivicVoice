package com.civicvoice.np.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.civicvoice.np.data.Category
import com.civicvoice.np.data.Poll
import com.civicvoice.np.ui.components.CreatePollDialog
import com.civicvoice.np.ui.theme.CivicVoiceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSuggestionScreen(
    onBackClick: () -> Unit,
    onSubmit: (String, String, Category, Boolean, String?, String?, Poll?) -> Unit,
    onSuccess: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf<String?>(null) }
    var authority by remember { mutableStateOf<String?>(null) }
    var showLocationDialog by remember { mutableStateOf(false) }
    var showAuthorityDialog by remember { mutableStateOf(false) }
    var showPollDialog by remember { mutableStateOf(false) }
    var poll by remember { mutableStateOf<Poll?>(null) }
    var selectedCategory by remember { mutableStateOf(Category.OTHER) }
    var isAnonymous by remember { mutableStateOf(false) }
    var showCategoryDialog by remember { mutableStateOf(false) }
    val isPostEnabled = title.isNotBlank() && description.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Suggestion") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            onSubmit(title, description, Category.OTHER, false, location, authority, poll)
                            onSuccess()
                        },
                        enabled = isPostEnabled,
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text("Post")
                    }
                }
            )
        },
        bottomBar = {
            // Action bar at the bottom
            Column {
                HorizontalDivider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // Placeholder icons for new features
                    IconButton(onClick = { showPollDialog = true }) {
                        Icon(Icons.Default.Poll, contentDescription = "Add Poll")
                    }
                    IconButton(onClick = { showLocationDialog = true }) {
                        Icon(Icons.Default.LocationOn, contentDescription = "Add Location")
                    }
                    IconButton(onClick = { showAuthorityDialog = true }) {
                        Icon(Icons.Default.Group, contentDescription = "Tag Authority")
                    }
                     IconButton(onClick = { /* TODO: Add image/video */ }) {
                        Icon(Icons.Default.Image, contentDescription = "Add Image")
                    }
                    IconButton(onClick = { showCategoryDialog = true }) {
                        Icon(Icons.Default.Category, contentDescription = "Select Category")
                    }
                    IconButton(onClick = { isAnonymous = !isAnonymous }) {
                        Icon(
                            imageVector = if (isAnonymous) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Post Anonymously"
                        )
                    }
                    IconButton(onClick = { /* TODO: AI Assist */ }) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = "AI Assist")
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // User info row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Avatar",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("John Doe", fontWeight = FontWeight.Bold) // Placeholder
                    // Maybe add user role or other info here later
                }
            }

            // Input fields
            TextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Title for your suggestion...", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 200.dp),
                placeholder = { Text("Share your thoughts...") },
                 colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )

            if (showLocationDialog) {
                InputDialog(
                    title = "Add Location",
                    onDismiss = { showLocationDialog = false },
                    onConfirm = {
                        location = it
                        showLocationDialog = false
                    }
                )
            }

            if (showPollDialog) {
                CreatePollDialog(
                    onDismiss = { showPollDialog = false },
                    onConfirm = {
                        poll = it
                        showPollDialog = false
                    }
                )
            }

            if (showAuthorityDialog) {
                InputDialog(
                    title = "Tag Authority",
                    onDismiss = { showAuthorityDialog = false },
                    onConfirm = {
                        authority = it
                        showAuthorityDialog = false
                    }
                )
            }

            if (showCategoryDialog) {
                CategorySelectionDialog(
                    onDismiss = { showCategoryDialog = false },
                    onConfirm = {
                        selectedCategory = it
                        showCategoryDialog = false
                    }
                )
            }

            // Chips for location and authority
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (selectedCategory != Category.OTHER) {
                    ChipWithRemove(text = "Category: ${selectedCategory.name.lowercase().replaceFirstChar { it.uppercase() }}") {
                        selectedCategory = Category.OTHER
                    }
                }
                if (poll != null) {
                    ChipWithRemove(text = "Poll: ${poll?.question}") {
                        poll = null
                    }
                }
                if (location != null) {
                    ChipWithRemove(text = "Location: $location") {
                        location = null
                    }
                }
                if (authority != null) {
                    ChipWithRemove(text = "Authority: $authority") {
                        authority = null
                    }
                }
            }
        }
    }
}

@Composable
fun ChipWithRemove(text: String, onRemove: () -> Unit) {
    InputChip(
        selected = true,
        onClick = { /* Nothing for now */ },
        label = { Text(text) },
        trailingIcon = {
            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove",
                )
            }
        }
    )
}

@Composable
fun CategorySelectionDialog(
    onDismiss: () -> Unit,
    onConfirm: (Category) -> Unit
) {
    var selectedCategory by remember { mutableStateOf(Category.OTHER) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Category") },
        text = {
            LazyColumn {
                items(Category.entries) { category ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedCategory = category }
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(category.name.lowercase().replaceFirstChar { it.uppercase() })
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(selectedCategory) }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun InputDialog(
    title: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter text") }
            )
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(text) },
                enabled = text.isNotBlank()
            ) {
                Text("Confirm")
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
fun CreateSuggestionScreenPreview() {
    CivicVoiceTheme {
        CreateSuggestionScreen(
            onBackClick = {},
            onSubmit = { _, _, _, _ -> },
            onSuccess = {}
        )
    }
}
