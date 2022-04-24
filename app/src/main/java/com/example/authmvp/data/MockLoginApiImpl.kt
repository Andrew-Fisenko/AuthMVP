package com.example.authmvp.data

import com.example.authmvp.domain.LoginApi

class MockLoginApiImpl : LoginApi {
    override fun login(login: String, password: String): Boolean {
        Thread.sleep(3000)
        return login == password
    }

    override fun register(login: String, password: String, email: String): Boolean {
        Thread.sleep(2000)
        return login.isNotEmpty()
    }

    override fun logout(): Boolean {
        Thread.sleep(2000)
        return true
    }

    override fun forgotPassword(login: String): Boolean {
        Thread.sleep(1000)
        return false
    }
}