package com.clarkelamothe.data.model

data class MarvelResponse<T>(
    val code: Int,
    val data: T,
    val status: String
)
