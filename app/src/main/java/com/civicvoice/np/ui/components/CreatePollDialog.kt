package com.civicvoice.np.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.civicvoice.np.data.Poll

@Composable
fun CreatePollDialog(
    onDismiss: () -> Unit,
    onConfirm: (Poll) -> Unit
) {
    var question by remember { mutableStateOf("") }
    var options by remember { mutableStateOf(listOf("", "")) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text("Create Poll", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = question,
                    onValueChange = { question = it },
                    label = { Text("Poll Question") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Options", style = MaterialTheme.typography.titleMedium)
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(options) { index, option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = option,
                                onValueChange = {
                                    val newOptions = options.toMutableList()
                                    newOptions[index] = it
                                    options = newOptions
                                },
                                label = { Text("Option ${index + 1}") },
                                modifier = Modifier.weight(1f)
                            )
                            if (options.size > 2) {
                                IconButton(onClick = {
                                    val newOptions = options.toMutableList()
                                    newOptions.removeAt(index)
                                    options = newOptions
                                }) {
                                    Icon(Icons.Default.Close, contentDescription = "Remove Option")
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = {
                        val newOptions = options.toMutableList()
                        newOptions.add("")
                        options = newOptions
                    },
                    enabled = options.size < 5
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Option")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Option")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val poll = Poll(question, options.filter { it.isNotBlank() })
                            onConfirm(poll)
                        },
                        enabled = question.isNotBlank() && options.all { it.isNotBlank() }
                    ) {
                        Text("Create")
                    }
                }
            }
        }
    }
}
