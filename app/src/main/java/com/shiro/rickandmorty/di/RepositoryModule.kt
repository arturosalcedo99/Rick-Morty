package com.shiro.rickandmorty.di

import com.shiro.rickandmorty.data.repositories.impl.CharactersRepositoryImpl
import com.shiro.rickandmorty.data.source.local.LocalCharactersDataSource
import com.shiro.rickandmorty.data.source.remote.RemoteCharactersDataSource
import com.shiro.rickandmorty.domain.repositories.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCharactersRepository(
        remoteCharactersDataSource: RemoteCharactersDataSource,
        localCharactersDataSource: LocalCharactersDataSource
    ): CharactersRepository =
        CharactersRepositoryImpl(remoteCharactersDataSource, localCharactersDataSource)
}