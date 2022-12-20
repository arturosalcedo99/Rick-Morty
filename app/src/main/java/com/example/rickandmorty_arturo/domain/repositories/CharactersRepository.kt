package com.example.rickandmorty_arturo.domain.repositories

import com.example.rickandmorty_arturo.data.models.CharacterResult
import com.example.rickandmorty_arturo.data.models.character.CharacterRemote
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getCharacters(page: Int): Flow<Result<CharacterResult?>>
    suspend fun getCharacterDetails(characterId: Int): Flow<Result<CharacterRemote?>>
    suspend fun searchCharacters(name: String, page: Int): Flow<Result<CharacterResult?>>
}