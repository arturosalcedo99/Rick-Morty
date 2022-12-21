package com.example.rickandmorty_arturo.data.source.local

import com.example.rickandmorty_arturo.data.models.character.CharacterLocal
import com.example.rickandmorty_arturo.data.models.character.CharacterRemote

interface LocalCharactersDataSource {
    fun getCharacters(page: Int): List<CharacterRemote>
    fun getCharacterDetails(characterId: Int): CharacterRemote
    fun searchCharacters(name: String): List<CharacterRemote>
    fun insertCharacter(character: CharacterLocal)
}