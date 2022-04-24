package com.example.authmvp.domain

import androidx.annotation.MainThread

interface LoginInteractor {
    fun login(
        login: String,
        password: String,
        @MainThread callback: (Boolean) -> Unit
    )
}

