package com.clarkelamothe.data.service

import com.clarkelamothe.data.model.CharacterRes
import com.clarkelamothe.data.model.ComicRes
import com.clarkelamothe.data.model.Data
import com.clarkelamothe.data.model.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersServices {

    @GET("characters")
    suspend fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Response<MarvelResponse<Data<CharacterRes>>>

    @GET("characters/{characterId}/comics")
    suspend fun getComicsByCharacterId(
        @Path("characterId") characterId: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: String,
    ): Response<MarvelResponse<Data<ComicRes>>>
}