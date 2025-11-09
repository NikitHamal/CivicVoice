package com.civicvoice.np.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

object MockRepository {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _suggestions = MutableStateFlow(getMockSuggestions())
    val suggestions: StateFlow<List<Suggestion>> = _suggestions.asStateFlow()

    private val _comments = MutableStateFlow(getMockComments())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    fun login(name: String, role: UserRole) {
        _currentUser.value = User(
            id = UUID.randomUUID().toString(),
            name = name,
            email = "$name@example.com",
            role = role,
            verified = role == UserRole.AUTHORITY
        )
    }

    fun logout() {
        _currentUser.value = null
    }

    fun addSuggestion(suggestion: Suggestion) {
        _suggestions.value = listOf(suggestion) + _suggestions.value
    }

    fun votePoll(suggestionId: String, option: String) {
        _suggestions.value = _suggestions.value.map { suggestion ->
            if (suggestion.id == suggestionId && suggestion.poll != null) {
                val newVotes = suggestion.poll.votes.toMutableMap()
                newVotes[option] = (newVotes[option] ?: 0) + 1
                suggestion.copy(
                    poll = suggestion.poll.copy(
                        votes = newVotes,
                        userVotedOption = option
                    )
                )
            } else {
                suggestion
            }
        }
    }

    fun voteSuggestion(suggestionId: String, vote: Vote) {
        _suggestions.value = _suggestions.value.map { suggestion ->
            if (suggestion.id == suggestionId) {
                val voteChange = when {
                    vote == Vote.UP && suggestion.userVote != Vote.UP -> 1
                    vote == Vote.DOWN && suggestion.userVote != Vote.DOWN -> -1
                    vote == Vote.UP && suggestion.userVote == Vote.UP -> -1
                    vote == Vote.DOWN && suggestion.userVote == Vote.DOWN -> 1
                    else -> 0
                }
                suggestion.copy(
                    votes = suggestion.votes + voteChange,
                    userVote = if (suggestion.userVote == vote) Vote.NONE else vote
                )
            } else {
                suggestion
            }
        }
    }

    fun addComment(comment: Comment) {
        _comments.value = _comments.value + comment
        _suggestions.value = _suggestions.value.map { suggestion ->
            if (suggestion.id == comment.suggestionId) {
                suggestion.copy(commentCount = suggestion.commentCount + 1)
            } else {
                suggestion
            }
        }
    }

    fun updateSuggestionStatus(suggestionId: String, status: Status) {
        _suggestions.value = _suggestions.value.map { suggestion ->
            if (suggestion.id == suggestionId) {
                suggestion.copy(status = status)
            } else {
                suggestion
            }
        }
    }

    fun getCommentsForSuggestion(suggestionId: String): List<Comment> {
        return _comments.value.filter { it.suggestionId == suggestionId }
    }

    private fun getMockSuggestions(): List<Suggestion> {
        return listOf(
            Suggestion(
                id = "1",
                title = "Install Solar Panels on Government Buildings",
                content = "We should install solar panels on all government buildings to reduce electricity costs and promote renewable energy. This would save taxpayer money in the long run and set a good example for the community.",
                category = Category.ENVIRONMENT,
                status = Status.UNDER_REVIEW,
                authorId = "user1",
                authorName = "Rajesh Kumar",
                votes = 245,
                commentCount = 32,
                aiPriority = true,
                aiSummary = "High-impact environmental initiative with potential cost savings and public support. Feasibility assessment required for implementation timeline.",
                location = "Kathmandu Valley"
            ),
            Suggestion(
                id = "2",
                title = "Dedicated Bike Lanes on Main Roads",
                content = "Adding dedicated bike lanes would encourage cycling, reduce traffic congestion, and improve air quality. We need safer infrastructure for cyclists.",
                category = Category.TRANSPORTATION,
                status = Status.OPEN,
                authorId = "user2",
                authorName = "Sita Thapa",
                votes = 189,
                commentCount = 24,
                aiPriority = true,
                aiSummary = "Popular suggestion addressing transportation and environmental concerns. Requires traffic flow analysis and budget allocation.",
                location = "Pokhara"
            ),
            Suggestion(
                id = "3",
                title = "Free WiFi in Public Parks",
                content = "Providing free WiFi in public parks would make them more useful for students and remote workers, while encouraging people to spend more time outdoors.",
                category = Category.INFRASTRUCTURE,
                status = Status.IMPLEMENTED,
                authorId = "user3",
                authorName = "Anil Sharma",
                votes = 156,
                commentCount = 18,
                location = "Biratnagar"
            ),
            Suggestion(
                id = "4",
                title = "Community Composting Program",
                content = "Start a community composting program to reduce waste and create fertilizer for public gardens. This would help reduce landfill usage and promote sustainability.",
                category = Category.ENVIRONMENT,
                status = Status.OPEN,
                authorId = "user4",
                authorName = "Maya Gurung",
                votes = 134,
                commentCount = 15,
                location = "Lalitpur"
            ),
            Suggestion(
                id = "5",
                title = "Better Street Lighting in Residential Areas",
                content = "Many residential streets have inadequate lighting, which creates safety concerns at night. We need better street lights to improve security.",
                category = Category.SAFETY,
                status = Status.OPEN,
                authorId = "user5",
                authorName = "Dipak Rai",
                votes = 198,
                commentCount = 28,
                aiPriority = true,
                aiSummary = "Critical safety issue with broad community support. Priority areas identified through crime data analysis.",
                location = "Bhaktapur"
            ),
            Suggestion(
                id = "6",
                title = "Mobile Health Clinics for Rural Areas",
                content = "Deploy mobile health clinics to serve rural communities that lack easy access to healthcare facilities.",
                category = Category.HEALTH,
                status = Status.UNDER_REVIEW,
                authorId = "user6",
                authorName = "Dr. Binita Shrestha",
                votes = 167,
                commentCount = 22,
                location = "Chitwan"
            ),
            Suggestion(
                id = "7",
                title = "After-School STEM Programs",
                content = "Create free after-school programs focused on science, technology, engineering, and math to help students develop important skills.",
                category = Category.EDUCATION,
                status = Status.OPEN,
                authorId = "user7",
                authorName = "Prakash Tamang",
                votes = 143,
                commentCount = 19
            ),
            Suggestion(
                id = "8",
                title = "Fix Potholes on Ring Road",
                content = "The ring road has numerous potholes that damage vehicles and cause accidents. Immediate repair work is needed.",
                category = Category.INFRASTRUCTURE,
                status = Status.UNDER_REVIEW,
                authorId = "user8",
                authorName = "Anonymous",
                isAnonymous = true,
                votes = 223,
                commentCount = 34,
                aiPriority = true,
                aiSummary = "Critical infrastructure maintenance with safety implications. Budget approved, implementation timeline pending.",
                location = "Kathmandu"
            )
        )
    }

    private fun getMockComments(): List<Comment> {
        return listOf(
            Comment(
                id = "c1",
                suggestionId = "1",
                authorId = "user10",
                authorName = "Ramesh Khadka",
                text = "This is an excellent idea! I've seen similar projects work well in other cities.",
                verified = false
            ),
            Comment(
                id = "c2",
                suggestionId = "1",
                authorId = "auth1",
                authorName = "District Officer",
                text = "We are currently evaluating the feasibility and costs. Will update soon.",
                verified = true
            ),
            Comment(
                id = "c3",
                suggestionId = "2",
                authorId = "user11",
                authorName = "Priya Lama",
                text = "As a daily cyclist, I fully support this. Safety is a major concern.",
                verified = false
            ),
            Comment(
                id = "c4",
                suggestionId = "5",
                authorId = "user12",
                authorName = "Krishna Bahadur",
                text = "My neighborhood desperately needs this. Thank you for bringing it up!",
                verified = false
            )
        )
    }
}
