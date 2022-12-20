package com.example.rickandmorty_arturo.data.source.remote.api

import com.example.rickandmorty_arturo.data.models.CharacterResult
import com.example.rickandmorty_arturo.data.models.character.CharacterRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    @GET("character/")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<CharacterResult>

    @GET("character/{characterId}")
    suspend fun getCharacterDetails(
        @Path("characterId") characterId: Int
    ): Response<CharacterRemote>

    @GET("character/")
    suspend fun searchCharacters(
        @Query("name") name: String,
        @Query("page") page: Int
    ): Response<CharacterResult>
}