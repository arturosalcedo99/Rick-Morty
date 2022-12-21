package com.shiro.rickandmorty.di

import com.shiro.rickandmorty.data.source.local.LocalCharactersDataSource
import com.shiro.rickandmorty.data.source.local.database.CharactersDao
import com.shiro.rickandmorty.data.source.local.impl.LocalCharactersDataSourceImpl
import com.shiro.rickandmorty.data.source.remote.RemoteCharactersDataSource
import com.shiro.rickandmorty.data.source.remote.api.CharactersApi
import com.shiro.rickandmorty.data.source.remote.impl.RemoteCharactersDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    /*REMOTE*/
    @Provides
    @Singleton
    fun provideRemoteCharactersDataSource(
        charactersApi: CharactersApi
    ): RemoteCharactersDataSource =
        RemoteCharactersDataSourceImpl(charactersApi)

    /*LOCAL*/
    @Provides
    @Singleton
    fun provideLocalCharacterDataSource(
        charactersDao: CharactersDao
    ): LocalCharactersDataSource =
        LocalCharactersDataSourceImpl(charactersDao)
}