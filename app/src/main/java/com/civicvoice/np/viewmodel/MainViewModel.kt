package com.civicvoice.np.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.civicvoice.np.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class MainViewModel : ViewModel() {
    val currentUser = MockRepository.currentUser
    val suggestions = MockRepository.suggestions
    val comments = MockRepository.comments

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _showOnboarding = MutableStateFlow(true)
    val showOnboarding: StateFlow<Boolean> = _showOnboarding.asStateFlow()

    fun login(name: String, role: UserRole) {
        MockRepository.login(name, role)
    }

    fun logout() {
        MockRepository.logout()
    }

    fun completeOnboarding() {
        _showOnboarding.value = false
    }

    fun setSelectedTab(tab: Int) {
        _selectedTab.value = tab
    }

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun addSuggestion(
        title: String,
        content: String,
        category: Category,
        isAnonymous: Boolean
    ) {
        viewModelScope.launch {
            val user = currentUser.value ?: return@launch
            val suggestion = Suggestion(
                id = UUID.randomUUID().toString(),
                title = title,
                content = content,
                category = category,
                status = Status.OPEN,
                authorId = user.id,
                authorName = if (isAnonymous) "Anonymous" else user.name,
                isAnonymous = isAnonymous,
                votes = 0,
                commentCount = 0
            )
            MockRepository.addSuggestion(suggestion)
        }
    }

    fun voteSuggestion(suggestionId: String, vote: Vote) {
        MockRepository.voteSuggestion(suggestionId, vote)
    }

    fun addComment(suggestionId: String, text: String) {
        viewModelScope.launch {
            val user = currentUser.value ?: return@launch
            val comment = Comment(
                id = UUID.randomUUID().toString(),
                suggestionId = suggestionId,
                authorId = user.id,
                authorName = user.name,
                text = text,
                verified = user.role == UserRole.AUTHORITY
            )
            MockRepository.addComment(comment)
        }
    }

    fun updateSuggestionStatus(suggestionId: String, status: Status) {
        MockRepository.updateSuggestionStatus(suggestionId, status)
    }

    fun getCommentsForSuggestion(suggestionId: String): List<Comment> {
        return MockRepository.getCommentsForSuggestion(suggestionId)
    }

    fun getFilteredSuggestions(filter: String): List<Suggestion> {
        return when (filter) {
            "Trending" -> suggestions.value.sortedByDescending { it.votes }
            "Nearby" -> suggestions.value.filter { it.location != null }
            else -> suggestions.value
        }
    }

    fun getSuggestionsByCategory(category: Category?): List<Suggestion> {
        return if (category == null) {
            suggestions.value
        } else {
            suggestions.value.filter { it.category == category }
        }
    }

    fun getSuggestionsByStatus(status: Status?): List<Suggestion> {
        return if (status == null) {
            suggestions.value
        } else {
            suggestions.value.filter { it.status == status }
        }
    }
}
