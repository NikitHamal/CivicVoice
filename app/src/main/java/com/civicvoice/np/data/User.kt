package com.civicvoice.np.data

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val verified: Boolean = false,
    val avatarUrl: String? = null,
    val badges: List<String> = emptyList(),
    val joinedDate: Long = System.currentTimeMillis()
)

enum class UserRole {
    CITIZEN,
    EXPERT,
    AUTHORITY
}
