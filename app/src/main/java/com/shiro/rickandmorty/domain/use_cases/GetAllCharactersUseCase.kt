package com.shiro.rickandmorty.domain.use_cases

import com.shiro.rickandmorty.domain.models.CharacterResult
import com.shiro.rickandmorty.domain.repositories.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(page: Int): Flow<Result<CharacterResult?>> =
        charactersRepository.getAllCharacters(page)
}