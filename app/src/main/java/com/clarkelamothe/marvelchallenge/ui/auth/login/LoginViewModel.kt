package com.clarkelamothe.marvelchallenge.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clarkelamothe.domain.model.User
import com.clarkelamothe.marvelchallenge.utils.isEmailValid
import com.clarkelamothe.marvelchallenge.utils.isPasswordValid
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private val _loginFormState = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginFormState

    fun loginDataChanged(user: User) {
        with(user) {
            when {
                !isEmailValid(email) ->
                    _loginFormState.value = LoginFormState(emailError = true)
                !isPasswordValid(password) ->
                    _loginFormState.value = LoginFormState(passwordError = true)
                else -> _loginFormState.value = LoginFormState(isDataValid = true)
            }
        }
    }
}

data class LoginFormState(
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val isDataValid: Boolean = false
)