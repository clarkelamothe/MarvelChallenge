package com.clarkelamothe.data.utils

import com.clarkelamothe.data.BuildConfig

object ApiDetails {
    const val TS = 1
    const val PRIVATE_KEY = BuildConfig.privateKey
    const val PUBLIC_KEY = "325caf1f852a9d38f9c019ad99cb2ace"

    private const val API_VERSION = "/v1/public"
    const val API_BASE_URL = "https://gateway.marvel.com/$API_VERSION"

}