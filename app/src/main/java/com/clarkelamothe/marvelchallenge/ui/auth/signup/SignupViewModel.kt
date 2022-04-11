package com.clarkelamothe.marvelchallenge.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clarkelamothe.marvelchallenge.utils.isEmailValid
import com.clarkelamothe.marvelchallenge.utils.isPasswordValid

class SignupViewModel : ViewModel() {
    private val _signupFormState = MutableLiveData<SignupFormState>()
    val signupFormState: LiveData<SignupFormState> = _signupFormState

    fun signupDataChanged(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        when {
            username.length < 5 ->
                _signupFormState.value = SignupFormState(userNameError = true)
            !isEmailValid(email) ->
                _signupFormState.value = SignupFormState(emailError = true)
            !isPasswordValid(password) ->
                _signupFormState.value = SignupFormState(passwordError = true)
            password != confirmPassword ->
                _signupFormState.value = SignupFormState(confirmPasswordError = true)
            else -> _signupFormState.value = SignupFormState(isDataValid = true)
        }
    }
}

data class SignupFormState(
    val userNameError: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val confirmPasswordError: Boolean = false,
    val isDataValid: Boolean = false
)