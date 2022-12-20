package com.example.rickandmorty_arturo.di

import com.example.rickandmorty_arturo.data.repositories.impl.CharactersRepositoryImpl
import com.example.rickandmorty_arturo.domain.data_sources.LocalCharactersDataSource
import com.example.rickandmorty_arturo.domain.data_sources.RemoteCharactersDataSource
import com.example.rickandmorty_arturo.domain.repositories.CharactersRepository
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