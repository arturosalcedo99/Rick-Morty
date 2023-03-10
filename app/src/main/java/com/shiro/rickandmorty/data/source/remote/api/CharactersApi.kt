package com.shiro.rickandmorty.data.source.remote.api

import com.shiro.rickandmorty.data.models.CharacterResultRemote
import com.example.rickandmorty_arturo.data.models.character.CharacterRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    @GET("character/")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<CharacterResultRemote>

    @GET("character/{characterId}")
    suspend fun getCharacterDetails(
        @Path("characterId") characterId: Int
    ): Response<CharacterRemote>

    @GET("character/")
    suspend fun searchCharacters(
        @Query("name") name: String,
        @Query("page") page: Int
    ): Response<CharacterResultRemote>
}