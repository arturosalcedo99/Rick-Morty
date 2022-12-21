package com.shiro.rickandmorty.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shiro.rickandmorty.data.models.character.CharacterLocal
import com.shiro.rickandmorty.data.models.character.location.CharacterLocationLocal
import com.shiro.rickandmorty.data.models.character.origin.CharacterOriginLocal

@Database(
    entities = [CharacterLocal::class, CharacterOriginLocal::class, CharacterLocationLocal::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}