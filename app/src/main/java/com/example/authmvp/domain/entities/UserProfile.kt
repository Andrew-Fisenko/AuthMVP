package com.example.authmvp.domain.entities

data class UserProfile(
    val id: String,
    val password: String,
    val email: String,
    val avatarUrl: String,
    val pin: Int
)

