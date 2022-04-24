package com.example.authmvp.ui.login

import androidx.annotation.MainThread

class LoginContract {

    interface View {
        @MainThread
        fun setSuccess()

        @MainThread
        fun setError(error: String)

        @MainThread
        fun showProgress()

        @MainThread
        fun hideProgress()
    }

    interface Presenter {
        @MainThread
        fun onAttach(view: View)

        @MainThread
        fun onLogin(login: String, password: String)

        @MainThread
        fun onCredentialsChanged()
    }
}