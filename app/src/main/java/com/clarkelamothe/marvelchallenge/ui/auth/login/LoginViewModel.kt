package com.clarkelamothe.marvelchallenge.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clarkelamothe.domain.model.User
import com.clarkelamothe.marvelchallenge.utils.isEmailValid
import com.clarkelamothe.marvelchallenge.utils.isPasswordValid

class LoginViewModel : ViewModel() {
    private val _loginFormState = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginFormState

    // validate all login fields
    fun loginDataChanged(user: User) {
        with(user) {
            when {
                !isEmailValid(email) -> LoginFormState("Email eror")
                !isPasswordValid(password) -> LoginFormState("Password Error")
                else -> LoginFormState(isDataValid = true)
            }
        }
    }

//    fun login(user: User) = viewModelScope.launch(Dispatchers.Main) {
//        val result = withContext(Dispatchers.IO) {
//            loginRepository.login(
//                loginDetails.email,
//                loginDetails.password
//            )
//        }
//        if (result.isSuccessful()) {
//            //result.data?.let { preference.saveUserToken(it.token) }
//            preference.saveUserToken("token de prueba")
//        }
//        _loginResponse.value = result
//    }


    data class LoginFormState(
        val emailError: String? = null,
        val passwordError: String? = null,
        val isDataValid: Boolean = false
    )
}