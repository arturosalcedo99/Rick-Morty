package com.shiro.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.shiro.rickandmorty.data.source.local.database.AppDatabase
import com.shiro.rickandmorty.data.source.local.database.CharactersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "RickAndMortyDatabase.db"
        ).build()

    @Provides
    fun provideCharactersDao(
        appDatabase: AppDatabase
    ): CharactersDao =
        appDatabase.charactersDao()
}