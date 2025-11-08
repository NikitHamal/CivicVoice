package com.civicvoice.np.data

data class Comment(
    val author: User,
    val text: String,
    val timestamp: Long
)
