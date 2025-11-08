package com.civicvoice.np.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.civicvoice.np.ui.theme.CivicVoiceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorityDashboardScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Authority Dashboard") },
                actions = {
                    IconButton(onClick = { /* Handle notification click */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FilterSection()
            StatsSection()
            ChartSection(title = "Issues by Category", value = "5,432", change = "+12.5%")
            ChartSection(title = "Submission Trends", value = "1,204", change = "-2.1%")
        }
    }
}

@Composable
fun FilterSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterDropdown(modifier = Modifier.weight(1f), text = "Category: All")
        FilterDropdown(modifier = Modifier.weight(1f), text = "Status: Open")
        FilterDropdown(modifier = Modifier.weight(1f), text = "Date: Last 30d")
    }
}

@Composable
fun FilterDropdown(modifier: Modifier = Modifier, text: String) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(text = { Text("Option 1") }, onClick = { expanded = false })
            DropdownMenuItem(text = { Text("Option 2") }, onClick = { expanded = false })
        }
    }
}

@Composable
fun StatsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatCard(modifier = Modifier.weight(1f), title = "Total Open Issues", value = "1,240")
        StatCard(modifier = Modifier.weight(1f), title = "New This Week", value = "86", change = "+5.2%")
        StatCard(modifier = Modifier.weight(1f), title = "Resolved This Month", value = "212", change = "+8.1%")
    }
}

@Composable
fun StatCard(modifier: Modifier = Modifier, title: String, value: String, change: String? = null) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            if (change != null) {
                Text(text = change, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun ChartSection(title: String, value: String, change: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = change, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
            }
            // Placeholder for chart
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(150.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AuthorityDashboardScreenPreview() {
    CivicVoiceTheme {
        AuthorityDashboardScreen()
    }
}
