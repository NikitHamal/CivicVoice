package com.civicvoice.np.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.civicvoice.np.ui.theme.CivicVoiceTheme

@Composable
fun AuthorityDashboardScreen() {
    Column {
        Text(text = "Authority Dashboard Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun AuthorityDashboardScreenPreview() {
    CivicVoiceTheme {
        AuthorityDashboardScreen()
    }
}
