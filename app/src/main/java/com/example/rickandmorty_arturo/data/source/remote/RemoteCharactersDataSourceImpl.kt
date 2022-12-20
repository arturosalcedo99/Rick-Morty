package com.example.rickandmorty_arturo.data.source.remote

import com.example.rickandmorty_arturo.data.models.CharacterResult
import com.example.rickandmorty_arturo.data.models.character.CharacterRemote
import com.example.rickandmorty_arturo.data.source.remote.api.CharactersApi
import com.example.rickandmorty_arturo.domain.data_sources.RemoteCharactersDataSource
import javax.inject.Inject

class RemoteCharactersDataSourceImpl @Inject constructor(
    private val charactersApi: CharactersApi
): RemoteCharactersDataSource {
    override suspend fun getCharacters(page: Int): CharacterResult? {
        return charactersApi.getCharacters(page).body()
    }

    override suspend fun getCharacterDetails(characterId: Int): CharacterRemote? {
        return charactersApi.getCharacterDetails(characterId).body()
    }

    override suspend fun searchCharacters(name: String, page: Int): CharacterResult? {
        return charactersApi.searchCharacters(name, page).body()
    }
}