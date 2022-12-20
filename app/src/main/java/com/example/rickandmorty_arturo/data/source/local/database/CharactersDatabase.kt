package com.example.rickandmorty_arturo.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmorty_arturo.data.models.character.CharacterLocal
import com.example.rickandmorty_arturo.data.models.character.location.CharacterLocationLocal
import com.example.rickandmorty_arturo.data.models.character.origin.CharacterOriginLocal

@Database(
    entities = [CharacterLocal::class, CharacterOriginLocal::class, CharacterLocationLocal::class],
    version = 1
)
abstract class CharactersDatabase: RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}