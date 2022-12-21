package com.shiro.rickandmorty.data.source.local.impl

import com.shiro.rickandmorty.data.models.character.CharacterLocal
import com.shiro.rickandmorty.data.source.local.LocalCharactersDataSource
import com.shiro.rickandmorty.data.source.local.database.CharactersDao
import com.shiro.rickandmorty.data.source.remote.api.ApiError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalCharactersDataSourceImpl @Inject constructor(
    private val charactersDao: CharactersDao
): LocalCharactersDataSource {
    override suspend fun getAllCharacters(): List<CharacterLocal>? {
        return charactersDao.getAllCharacters()
    }

    override suspend fun getCharacterDetails(characterId: Int): CharacterLocal? {
        return charactersDao.getCharacterDetails(characterId)
    }

    override suspend fun searchCharacters(name: String): List<CharacterLocal>? {
        return charactersDao.searchCharacters(name)
    }

    override suspend fun saveCharacter(character: CharacterLocal) {
        charactersDao.insertCharacter(character)
    }
}