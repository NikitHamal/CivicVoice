package com.civicvoice.np.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.civicvoice.np.data.Suggestion
import com.civicvoice.np.ui.theme.CivicVoiceTheme
import com.civicvoice.np.viewmodels.MockViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSuggestionScreen(
    navController: NavController,
    viewModel: MockViewModel = viewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var isAnonymous by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            // TODO: Add category dropdown
            Row {
                Text(text = "Post anonymously")
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = isAnonymous, onCheckedChange = { isAnonymous = it })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /* Handle improve clarity */ }) {
                Text(text = "✨ Improve Clarity")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val newSuggestion = Suggestion(
                    title = title,
                    content = description,
                    category = "Other",
                    status = "Open",
                    votes = 0,
                    comments = emptyList(),
                    isAiPriority = false
                )
                viewModel.addSuggestion(newSuggestion)
                scope.launch {
                    snackbarHostState.showSnackbar("Suggestion posted")
                }
                navController.popBackStack()
            }) {
                Text(text = "Submit")
            }
        }
    }
}

@Preview
@Composable
fun CreateSuggestionScreenPreview() {
    CivicVoiceTheme {
        CreateSuggestionScreen(navController = NavController(androidx.compose.ui.platform.LocalContext.current))
    }
}
