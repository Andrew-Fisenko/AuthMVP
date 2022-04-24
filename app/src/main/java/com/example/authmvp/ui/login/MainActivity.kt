package com.example.authmvp.ui.login

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.authmvp.app
import com.example.authmvp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var binding: ActivityMainBinding
    private var presenter: LoginContract.Presenter? = null
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = restorePresenter()
        presenter?.onAttach(this)
        binding.forgetPass.isVisible = false
        binding.progressBar.isVisible = false

        binding.authButton.setOnClickListener {
            presenter?.onLogin(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
    }

    private fun restorePresenter(): LoginPresenter {
        val presenter = lastCustomNonConfigurationInstance as? LoginPresenter
        return presenter ?: LoginPresenter(app.loginInteractor)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }

    @MainThread
    override fun setSuccess() {
        binding.authButton.isVisible = false
        binding.forgetPass.isVisible = false
        binding.registration.isVisible = false
        binding.root.setBackgroundColor(Color.GREEN)
    }

    @MainThread
    override fun setError(error: String) {
        binding.forgetPass.isVisible = true
        Toast.makeText(this, "ERROR $error", Toast.LENGTH_SHORT).show()
        binding.root.setBackgroundColor(Color.RED)
    }

    @MainThread
    override fun showProgress() {
        binding.authButton.isEnabled = false
        binding.progressBar.isVisible = true
        postProgress()
        hideKeyboard(this)
    }

    @MainThread
    override fun hideProgress() {
        binding.authButton.isEnabled = true
    }

    private fun hideKeyboard(activity: Activity) {
        val imn: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imn.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun postProgress() {
        var progressStatus = binding.progressBar!!.progress
        Thread(Runnable {
            while (progressStatus < 100) {
                progressStatus += 5
                handler.post(Runnable {
                    binding.progressBar!!.progress = progressStatus
                })
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }).start()
    }
}
