package com.clarkelamothe.marvelchallenge.ui.auth.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.widget.doAfterTextChanged
import com.clarkelamothe.domain.model.User
import com.clarkelamothe.marvelchallenge.databinding.ActivityLoginBinding
import com.clarkelamothe.marvelchallenge.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        onButtonClick()
        onTextChanged()
        observeFormState()
    }

    private fun onButtonClick() {
        with(binding) {
            btnLogin.setOnClickListener {
            }
            btnSignup.setOnClickListener {
                Toast.makeText(this@LoginActivity, "Signup", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onTextChanged() {
        with(binding) {
            etEmail.doAfterTextChanged {
                loginViewModel.loginDataChanged(
                    User(
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    )
                )
            }
            etPassword.doAfterTextChanged {
                loginViewModel.loginDataChanged(
                    User(
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    )
                )
            }
        }
    }

    private fun observeFormState() {
        loginViewModel.loginFormState.observe(this) {
            with(binding) {
                btnLogin.isEnabled = it.isDataValid
                when {
                    it.emailError && etEmail.text?.isNotBlank() == true -> etEmail.error =
                        "Email Error"
                    it.passwordError && etPassword.text?.isNotBlank() == true -> etPassword.error =
                        "Password Error"
                    else -> {
                        btnLogin.setOnClickListener {
                            signIn(User(etEmail.text.toString(), etPassword.text.toString()))
                        }
                    }
                }
            }
        }
    }

    private fun signIn(user: User) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(Activity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(baseContext, "login success", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    Log.d("LOGGED IN USER", "${user?.email}")
                    goToHome()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        // [END sign_in_with_email]
    }

    private fun goToHome(){
        startActivity(Intent(this, HomeActivity::class.java) )
    }
}