package com.example.rickandmorty_arturo.di

import com.example.rickandmorty_arturo.data.source.local.impl.LocalCharactersDataSourceImpl
import com.example.rickandmorty_arturo.data.source.local.database.CharactersDatabase
import com.example.rickandmorty_arturo.data.source.remote.impl.RemoteCharactersDataSourceImpl
import com.example.rickandmorty_arturo.data.source.remote.api.CharactersApi
import com.example.rickandmorty_arturo.data.source.local.LocalCharactersDataSource
import com.example.rickandmorty_arturo.data.source.remote.RemoteCharactersDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    /**********
     * REMOTE *
     **********/
    @Provides
    fun provideRemoteCharactersDataSource(
        charactersApi: CharactersApi
    ): RemoteCharactersDataSource =
        RemoteCharactersDataSourceImpl(charactersApi)

    /**********
     * LOCAL *
     **********/
    @Provides
    fun provideLocalCharactersDataSource(
        charactersDatabase: CharactersDatabase
    ): LocalCharactersDataSource =
        LocalCharactersDataSourceImpl(charactersDatabase.charactersDao())
}

