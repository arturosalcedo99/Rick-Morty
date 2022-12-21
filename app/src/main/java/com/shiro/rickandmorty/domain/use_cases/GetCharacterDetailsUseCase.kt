package com.shiro.rickandmorty.domain.use_cases

import com.shiro.rickandmorty.domain.models.Character
import com.shiro.rickandmorty.domain.repositories.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(characterId: Int): Flow<Result<Character?>> =
        charactersRepository.getCharacterDetails(characterId)
}