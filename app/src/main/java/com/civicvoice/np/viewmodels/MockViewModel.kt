package com.civicvoice.np.viewmodels

import androidx.lifecycle.ViewModel
import com.civicvoice.np.data.Comment
import com.civicvoice.np.data.Suggestion
import com.civicvoice.np.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockViewModel : ViewModel() {

    private val _suggestions = MutableStateFlow<List<Suggestion>>(emptyList())
    val suggestions: StateFlow<List<Suggestion>> = _suggestions

    init {
        _suggestions.value = listOf(
            Suggestion(
                title = "Improve public transportation",
                content = "The current public transportation system is inadequate for the needs of the city. We need to invest in new buses and routes. This will help to reduce traffic congestion and improve air quality.",
                category = "Transportation",
                status = "Open",
                votes = 123,
                comments = listOf(
                    Comment(
                        author = User(name = "Jane Doe", role = "Citizen", verified = true),
                        text = "I completely agree! The buses are always late and overcrowded.",
                        timestamp = System.currentTimeMillis()
                    )
                ),
                isAiPriority = true
            ),
            Suggestion(
                title = "Build a new park",
                content = "Our community needs more green spaces. A new park would provide a place for children to play and for people to relax and enjoy nature.",
                category = "Recreation",
                status = "Under Review",
                votes = 78,
                comments = emptyList(),
                isAiPriority = false
            )
        )
    }

    fun addSuggestion(suggestion: Suggestion) {
        _suggestions.value = _suggestions.value + suggestion
    }
}
