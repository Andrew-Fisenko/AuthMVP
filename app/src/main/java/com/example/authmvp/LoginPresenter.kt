package com.example.authmvp

import android.os.Handler
import android.os.Looper
import java.lang.Thread.sleep

class LoginPresenter : LoginContract.Presenter {
    private var view: LoginContract.View? = null
    private val uiHandler = Handler(Looper.getMainLooper())
    private var currentResut: Boolean = false
    private var errorText: String = "ERROR"

    override fun onAttach(view: LoginContract.View) {
        this.view = view
        if (currentResut) {
            view.setSuccess()
        }
    }

    override fun onLogin(login: String, password: String) {
        view?.showProgress()
        Thread {
            sleep(1000)
            uiHandler.post {
                view?.hideProgress()
                if (checkCredentials(login, password)) {
                    view?.setSuccess()
                    currentResut = true
                    errorText = ""
                } else {
                    errorText = "Invalid password!"
                    view?.setError(errorText)
                    currentResut = false
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