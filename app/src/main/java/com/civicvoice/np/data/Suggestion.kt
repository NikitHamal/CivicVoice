package com.civicvoice.np.data

data class Suggestion(
    val title: String,
    val content: String,
    val category: String,
    val status: String,
    val votes: Int,
    val comments: List<Comment>,
    val isAiPriority: Boolean
)
