package com.shiro.rickandmorty.data.source.local

import com.shiro.rickandmorty.data.models.character.CharacterLocal
import kotlinx.coroutines.flow.Flow

interface LocalCharactersDataSource {
    suspend fun getAllCharacters(): List<CharacterLocal>?
    suspend fun getCharacterDetails(characterId: Int): CharacterLocal?
    suspend fun searchCharacters(name: String): List<CharacterLocal>?
    suspend fun saveCharacter(character: CharacterLocal)
}