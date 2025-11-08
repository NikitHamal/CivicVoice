package com.civicvoice.np.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.civicvoice.np.composables.SuggestionCard
import com.civicvoice.np.ui.theme.CivicVoiceTheme
import com.civicvoice.np.viewmodels.MockViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MockViewModel = viewModel()) {
    val suggestions by viewModel.suggestions.collectAsState()
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("All", "Trending", "Nearby", "Category")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "CivicVoice") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation icon click */ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle search icon click */ }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("create_suggestion") }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Suggestion")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(selectedTabIndex = tabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        text = { Text(text = title) }
                    )
                }
            }
            LazyColumn {
                items(suggestions) { suggestion ->
                    SuggestionCard(
                        title = suggestion.title,
                        summary = suggestion.content,
                        category = suggestion.category,
                        upvotes = suggestion.votes,
                        comments = suggestion.comments.size,
                        status = suggestion.status,
                        isAiPriority = suggestion.isAiPriority
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    CivicVoiceTheme {
        HomeScreen(navController = NavController(androidx.compose.ui.platform.LocalContext.current))
    }
}
