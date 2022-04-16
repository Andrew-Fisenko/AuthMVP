package com.example.authmvp

import android.os.Handler
import android.os.Looper
import java.lang.Thread.sleep

class LoginPresenter : LoginContract.Presenter {
    private var view: LoginContract.View? = null
    private val uiHandler = Handler(Looper.getMainLooper())
    private var currentSucsess: Boolean = false
    private var currentError: Boolean = false
    private var errorText: String = "ERROR"

    override fun onAttach(view: LoginContract.View) {
        this.view = view
        if (currentSucsess) {
            view.setSuccess()
        } else if (currentError) {
            view.setError(errorText)
        }
    }

    override fun onLogin(login: String, password: String) {
        view?.showProgress()
        Thread {
            sleep(2000)
            uiHandler.post {
                view?.hideProgress()
                if (checkCredentials(login, password)) {
                    view?.setSuccess()
                    currentSucsess = true
                    errorText = ""
                } else {
                    errorText = "Invalid password!"
                    view?.setError(errorText)
                    currentError = true
                }
            }
        }.start()
    }

    private fun checkCredentials(login: String, password: String): Boolean {
        return login == password
    }

    override fun onCredentialsChanged() {
        // TODO("Not yet implemented")
    }
}