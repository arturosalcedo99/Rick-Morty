package com.example.rickandmorty_arturo.data.source.remote

import com.example.rickandmorty_arturo.data.models.CharacterResult
import com.example.rickandmorty_arturo.data.models.character.CharacterRemote

interface RemoteCharactersDataSource {
    suspend fun getCharacters(page: Int): CharacterResult?
    suspend fun getCharacterDetails(characterId: Int): CharacterRemote?
    suspend fun searchCharacters(name: String, page: Int): CharacterResult?
}