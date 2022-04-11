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
import com.clarkelamothe.marvelchallenge.ui.auth.signup.SignupActivity
import com.clarkelamothe.marvelchallenge.ui.home.HomeActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var fbCallbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        fbCallbackManager = CallbackManager.Factory.create()

        onBtnClick()
        onTextChanged()
        observeFormState()
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
                    it.emailError && etEmail.text?.isNotBlank() == true ->
                        etEmail.error = "Email Error"
                    it.passwordError && etPassword.text?.isNotBlank() == true ->
                        etPassword.error = "Password Error"
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
        auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(Activity()) { task ->
                if (task.isSuccessful) {
                    goTo(HomeActivity())
                } else {
                    Log.d("AUTH", "FAILED!")
                    Toast.makeText(
                        this@LoginActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun signInWithFb() {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, callbackManager = fbCallbackManager, listOf("email"))
        LoginManager.getInstance().registerCallback(callbackManager = fbCallbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    finish()
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(this@LoginActivity, "An error occurred", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onSuccess(result: LoginResult) {
                    result.let { it ->
                        val token = it.accessToken
                        val credential = FacebookAuthProvider.getCredential(token.token)
                        FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Log.d("FB AUTH", "${it.result.user?.email}")
                                    goTo(HomeActivity())
                                } else {
                                    finish()
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Fb login error",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                }
            })
    }

//    public override fun onStart() {
//        super.onStart()
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
////            goTo(HomeActivity())
//        }
//    }

    private fun onBtnClick() = with(binding) {
        btnSignup.setOnClickListener {
            goTo(SignupActivity())
        }
        btnFb.setOnClickListener {
//            signInWithFb()
        }
    }

    private fun goTo(activity: Activity) = startActivity(Intent(this, activity::class.java))
}