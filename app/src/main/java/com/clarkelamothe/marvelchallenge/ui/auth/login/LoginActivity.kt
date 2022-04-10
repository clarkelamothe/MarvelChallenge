package com.clarkelamothe.marvelchallenge.ui.auth.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.clarkelamothe.marvelchallenge.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onButtonClick()
    }

    private fun onButtonClick() {
        with(binding) {
            btnLogin.setOnClickListener {
                Toast.makeText(this@LoginActivity, "Login", Toast.LENGTH_SHORT).show()
            }
            btnSignup.setOnClickListener {
                Toast.makeText(this@LoginActivity, "Signup", Toast.LENGTH_SHORT).show()
            }
        }
    }

}