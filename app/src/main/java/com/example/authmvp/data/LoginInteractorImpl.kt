package com.example.authmvp.data

import android.os.Handler
import androidx.annotation.MainThread
import com.example.authmvp.domain.LoginApi
import com.example.authmvp.domain.LoginInteractor

class LoginInteractorImpl(
    private val api: LoginApi,
    private val uiHandler: Handler
) : LoginInteractor {
    override fun login(
        login: String,
        password: String,
        @MainThread callback: (Boolean) -> Unit
    ) {
        Thread {
            val result = api.login(login, password)
            uiHandler.post {
                callback(result)
            }
        }.start()
    }
}