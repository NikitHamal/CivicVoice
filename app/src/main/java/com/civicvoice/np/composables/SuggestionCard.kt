package com.civicvoice.np.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.civicvoice.np.ui.theme.CivicVoiceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestionCard(
    title: String,
    summary: String,
    category: String,
    upvotes: Int,
    comments: Int,
    status: String,
    isAiPriority: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { /* Handle card click */ }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = summary, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SuggestionChip(text = category)
                Spacer(modifier = Modifier.weight(1f))
                if (isAiPriority) {
                    Icon(
                        imageVector = Icons.Default.Whatshot,
                        contentDescription = "AI Priority",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                SuggestionChip(text = status)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = "Upvotes")
                Text(text = upvotes.toString())
                Spacer(modifier = Modifier.width(16.dp))
                Icon(imageVector = Icons.Default.Comment, contentDescription = "Comments")
                Text(text = comments.toString())
            }
        }
    }
}

@Composable
fun SuggestionChip(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview
@Composable
fun SuggestionCardPreview() {
    CivicVoiceTheme {
        SuggestionCard(
            title = "Improve public transportation",
            summary = "The current public transportation system is inadequate for the needs of the city. We need to invest in new buses and routes.",
            category = "Transportation",
            upvotes = 123,
            comments = 45,
            status = "Open",
            isAiPriority = true
        )
    }
}
