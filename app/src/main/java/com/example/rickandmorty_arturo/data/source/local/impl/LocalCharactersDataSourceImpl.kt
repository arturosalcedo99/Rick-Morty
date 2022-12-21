package com.example.rickandmorty_arturo.data.source.local.impl

import com.example.rickandmorty_arturo.data.mappers.toCharacterRemote
import com.example.rickandmorty_arturo.data.mappers.toCharacterRemoteList
import com.example.rickandmorty_arturo.data.models.character.CharacterLocal
import com.example.rickandmorty_arturo.data.models.character.CharacterRemote
import com.example.rickandmorty_arturo.data.source.local.LocalCharactersDataSource
import com.example.rickandmorty_arturo.data.source.local.database.CharactersDao
import javax.inject.Inject

class LocalCharactersDataSourceImpl @Inject constructor(
    private val charactersDao: CharactersDao
): LocalCharactersDataSource {
    override fun getCharacters(page: Int): List<CharacterRemote> {
        return charactersDao.getAllCharacters().toCharacterRemoteList()
    }

    override fun getCharacterDetails(characterId: Int): CharacterRemote {
        return charactersDao.getCharacterById(characterId).toCharacterRemote()
    }

    override fun searchCharacters(name: String): List<CharacterRemote> {
        return charactersDao.searchCharactersByName(name).toCharacterRemoteList()
    }

    override fun insertCharacter(character: CharacterLocal) {
        charactersDao.insertCharacter(character)
    }
}