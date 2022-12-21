package com.shiro.rickandmorty.domain.repositories

import com.shiro.rickandmorty.domain.models.Character
import com.shiro.rickandmorty.domain.models.CharacterResult
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getAllCharacters(page: Int): Flow<Result<CharacterResult?>>
    suspend fun getCharacterDetails(characterId: Int): Flow<Result<Character?>>
    suspend fun searchCharacters(name: String, page: Int): Flow<Result<CharacterResult?>>
}