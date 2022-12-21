package com.shiro.rickandmorty.data.source.remote.impl

import com.shiro.rickandmorty.data.models.CharacterResultRemote
import com.example.rickandmorty_arturo.data.models.character.CharacterRemote
import com.shiro.rickandmorty.data.source.remote.RemoteCharactersDataSource
import com.shiro.rickandmorty.data.source.remote.api.CharactersApi
import javax.inject.Inject

class RemoteCharactersDataSourceImpl @Inject constructor(
    private val charactersApi: CharactersApi
): RemoteCharactersDataSource {
    override suspend fun getAllCharacters(page: Int): CharacterResultRemote? {
        return charactersApi.getCharacters(page).body()
    }

    override suspend fun getCharacterDetails(characterId: Int): CharacterRemote? {
        return charactersApi.getCharacterDetails(characterId).body()
    }

    override suspend fun searchCharacters(name: String, page: Int): CharacterResultRemote? {
        return charactersApi.searchCharacters(name, page).body()
    }
}