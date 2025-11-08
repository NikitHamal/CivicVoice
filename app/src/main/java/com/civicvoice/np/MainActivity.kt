package com.civicvoice.np

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.civicvoice.np.navigation.AppNavigation
import com.civicvoice.np.ui.theme.CivicVoiceTheme
import com.civicvoice.np.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: MainViewModel = viewModel()
            val isDarkMode by viewModel.isDarkMode.collectAsState()

            CivicVoiceTheme(darkTheme = isDarkMode) {
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}
