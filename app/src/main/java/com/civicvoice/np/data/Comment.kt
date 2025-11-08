package com.civicvoice.np.data

data class Comment(
    val id: String,
    val suggestionId: String,
    val authorId: String,
    val authorName: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val verified: Boolean = false
)
