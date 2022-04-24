package com.example.authmvp.ui.login

import com.example.authmvp.domain.LoginInteractor

class LoginPresenter(
    private val loginInteractor: LoginInteractor
) : LoginContract.Presenter {
    private var view: LoginContract.View? = null
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

        loginInteractor.login(login, password) { result ->
            view?.hideProgress()
            if (result) {
                view?.setSuccess()
                currentSucsess = true
                errorText = ""
            } else {
                errorText = "Invalid password!"
                view?.setError(errorText)
                currentError = true
            }
        }
    }

    private fun checkCredentials(login: String, password: String): Boolean {
        return login == password
    }

    override fun onCredentialsChanged() {
        // TODO("Not yet implemented")
    }
}