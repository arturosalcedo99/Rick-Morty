package com.shiro.rickandmorty.di

import com.shiro.rickandmorty.domain.repositories.CharactersRepository
import com.shiro.rickandmorty.domain.use_cases.GetAllCharactersUseCase
import com.shiro.rickandmorty.domain.use_cases.GetCharacterDetailsUseCase
import com.shiro.rickandmorty.domain.use_cases.GetCharactersBySearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetAllCharactersUseCase(
        charactersRepository: CharactersRepository
    ): GetAllCharactersUseCase = GetAllCharactersUseCase(charactersRepository)

    @Provides
    @Singleton
    fun provideGetCharacterDetailsUseCase(
        charactersRepository: CharactersRepository
    ): GetCharacterDetailsUseCase = GetCharacterDetailsUseCase(charactersRepository)

    @Provides
    @Singleton
    fun provideGetCharactersBySearchUseCase(
        charactersRepository: CharactersRepository
    ): GetCharactersBySearchUseCase = GetCharactersBySearchUseCase(charactersRepository)
}