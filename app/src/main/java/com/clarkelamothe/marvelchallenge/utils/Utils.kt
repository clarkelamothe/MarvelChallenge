package com.clarkelamothe.marvelchallenge.utils

import java.util.regex.Pattern

// A placeholder password validation check
fun isPasswordValid(password: String): Boolean = password.length > 5

// A placeholder email validation check
private val EMAIL_ADDRESS_PATTERN = Pattern.compile(
    "[a-zA-Z0-9+\\.\\_\\%\\-\\+]{1,256}" +
            "@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

fun isEmailValid(email: String): Boolean = EMAIL_ADDRESS_PATTERN.matcher(email).matches()