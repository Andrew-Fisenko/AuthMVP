package com.example.authmvp

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.authmvp.data.LoginInteractorImpl
import com.example.authmvp.data.MockLoginApiImpl
import com.example.authmvp.domain.LoginApi
import com.example.authmvp.domain.LoginInteractor

class App : Application() {
    private val api: LoginApi by lazy { MockLoginApiImpl() }
    val loginInteractor: LoginInteractor by lazy {
        LoginInteractorImpl(app.api, Handler(Looper.getMainLooper()))
    }
}

val Context.app: App
    get() {
        return applicationContext as App
    }