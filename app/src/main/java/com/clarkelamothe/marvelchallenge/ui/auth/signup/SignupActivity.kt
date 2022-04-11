package com.clarkelamothe.marvelchallenge.ui.auth.signup

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.clarkelamothe.domain.model.LoggedInUser
import com.clarkelamothe.marvelchallenge.databinding.ActivitySignupBinding
import com.clarkelamothe.marvelchallenge.ui.auth.login.LoginActivity
import com.clarkelamothe.marvelchallenge.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val signupViewModel: SignupViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        onClickLogin()
        onTextChanged()
        observeFormState()
    }

    private fun onTextChanged() {
        with(binding) {
            etUsername.doAfterTextChanged {
                signupViewModel.signupDataChanged(
                    etUsername.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString(),
                    etConfirmPass.text.toString()
                )
            }
            etEmail.doAfterTextChanged {
                signupViewModel.signupDataChanged(
                    etUsername.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString(),
                    etConfirmPass.text.toString()
                )
            }
            etPassword.doAfterTextChanged {
                signupViewModel.signupDataChanged(
                    etUsername.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString(),
                    etConfirmPass.text.toString()
                )
            }
            etConfirmPass.doAfterTextChanged {
                signupViewModel.signupDataChanged(
                    etUsername.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString(),
                    etConfirmPass.text.toString()
                )
            }
        }
    }

    private fun observeFormState() {
        signupViewModel.signupFormState.observe(this) {
            with(binding) {
                btnSignup.isEnabled = it.isDataValid
                when {
                    it.userNameError && etUsername.text?.isNotBlank() == true -> {
                        etUsername.error = "Username Error"
                    }
                    it.emailError && etPassword.text?.isNotBlank() == true -> {
                        etEmail.error = "Email Error"
                    }
                    it.passwordError && etPassword.text?.isNotBlank() == true -> {
                        etPassword.error = "Password Error"
                    }
                    etConfirmPass != etPassword && it.confirmPasswordError ->
                        etConfirmPass.error = "Passwords don't match"
                    else -> {
                        btnSignup.setOnClickListener {
                            createAccount(
                                etUsername.text.toString(),
                                etEmail.text.toString(),
                                etPassword.text.toString()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onClickLogin() = binding.tvLogin.setOnClickListener {
        goTo(LoginActivity())
    }

    private fun goTo(activity: Activity) = startActivity(Intent(this, activity::class.java))

    private fun createAccount(username: String, email: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val loggedInUser = LoggedInUser(username, user?.getIdToken(true).toString())
                    Log.d(TAG, "createUserWithEmail:success -----> ${loggedInUser.username}")
                    Toast.makeText(this, "Welcome $loggedInUser", Toast.LENGTH_SHORT).show()
                    goTo(HomeActivity())
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
//                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }
}