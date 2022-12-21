package com.example.rickandmorty_arturo.di

import com.example.rickandmorty_arturo.data.repositories.impl.CharactersRepositoryImpl
import com.example.rickandmorty_arturo.data.source.local.LocalCharactersDataSource
import com.example.rickandmorty_arturo.data.source.remote.RemoteCharactersDataSource
import com.example.rickandmorty_arturo.data.repositories.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideCharactersRepository(
        remoteCharactersDataSource: RemoteCharactersDataSource,
        localCharactersDataSource: LocalCharactersDataSource
    ): CharactersRepository =
        CharactersRepositoryImpl(
            remoteCharactersDataSource,
            localCharactersDataSource
        )
}