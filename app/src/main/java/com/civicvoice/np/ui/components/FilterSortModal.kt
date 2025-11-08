package com.civicvoice.np.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.civicvoice.np.data.Status

@Composable
fun FilterSortModal(
    onDismiss: () -> Unit,
    onApplyFilters: (String, Set<Status>) -> Unit
) {
    var sortOption by remember { mutableStateOf("Trending") }
    val sortOptions = listOf("Trending", "Most Upvoted", "Most Recent")

    var selectedStatus by remember { mutableStateOf(setOf<Status>()) }
    val statusOptions = Status.values().toList()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Filter & Sort", style = MaterialTheme.typography.titleLarge) },
        text = {
            Column {
                Text("Sort By:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                sortOptions.forEach { option ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (option == sortOption),
                                onClick = { sortOption = option }
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (option == sortOption),
                            onClick = { sortOption = option }
                        )
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Filter by Status:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                statusOptions.forEach { status ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (status in selectedStatus),
                                onClick = {
                                    selectedStatus = if (status in selectedStatus) {
                                        selectedStatus - status
                                    } else {
                                        selectedStatus + status
                                    }
                                }
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = status in selectedStatus,
                            onCheckedChange = {
                                selectedStatus = if (it) {
                                    selectedStatus + status
                                } else {
                                    selectedStatus - status
                                }
                            }
                        )
                        Text(
                            text = status.name.replace('_', ' ').lowercase().replaceFirstChar { it.titlecase() },
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onApplyFilters(sortOption, selectedStatus) }) {
                Text("Apply Filters")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
