package com.example.rickandmorty_arturo.data.source.local.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty_arturo.data.models.character.CharacterLocal

interface CharactersDao {

    @Query("SELECT * FROM CharacterLocal")
    fun getAllCharacters(): List<CharacterLocal>

    @Query("SELECT * FROM CharacterLocal WHERE id = :characterId")
    fun getCharacterById(characterId: Int): CharacterLocal

    @Query("SELECT * FROM CharacterLocal WHERE name LIKE '%' || :name")
    fun searchCharactersByName(name: String): List<CharacterLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: CharacterLocal)
}