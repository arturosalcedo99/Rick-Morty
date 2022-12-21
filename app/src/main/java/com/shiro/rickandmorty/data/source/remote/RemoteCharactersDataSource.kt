package com.shiro.rickandmorty.data.source.remote

import com.shiro.rickandmorty.data.models.CharacterResultRemote
import com.example.rickandmorty_arturo.data.models.character.CharacterRemote

interface RemoteCharactersDataSource {
    suspend fun getAllCharacters(page: Int): CharacterResultRemote?
    suspend fun getCharacterDetails(characterId: Int): CharacterRemote?
    suspend fun searchCharacters(name: String, page: Int): CharacterResultRemote?
}