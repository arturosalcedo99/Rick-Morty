package com.example.rickandmorty_arturo.di

import android.content.Context
import androidx.room.Room
import com.example.rickandmorty_arturo.data.source.local.database.CharactersDao
import com.example.rickandmorty_arturo.data.source.local.database.CharactersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): CharactersDatabase =
        Room.databaseBuilder(
            context,
            CharactersDatabase::class.java,
            "RickAndMortyDatabase.db"
        ).build()

    @Provides
    fun provideCharactersDao(charactersDatabase: CharactersDatabase): CharactersDao =
        charactersDatabase.charactersDao()
}