package com.civicvoice.np.data

data class Suggestion(
    val id: String,
    val title: String,
    val content: String,
    val category: Category,
    val status: Status,
    val authorId: String,
    val authorName: String,
    val isAnonymous: Boolean = false,
    val votes: Int = 0,
    val commentCount: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val aiPriority: Boolean = false,
    val aiSummary: String? = null,
    val location: String? = null,
    val authority: String? = null,
    val userVote: Vote = Vote.NONE,
    val poll: Poll? = null
)

data class Poll(
    val question: String,
    val options: List<String>,
    val votes: Map<String, Int> = emptyMap(),
    val userVotedOption: String? = null
)

enum class Category {
    INFRASTRUCTURE,
    EDUCATION,
    HEALTH,
    ENVIRONMENT,
    TRANSPORTATION,
    SAFETY,
    OTHER
}

enum class Status {
    OPEN,
    UNDER_REVIEW,
    IMPLEMENTED
}

enum class Vote {
    UP,
    DOWN,
    NONE
}
