package com.shiro.rickandmorty.domain.use_cases

import com.shiro.rickandmorty.data.source.remote.api.ApiError
import com.shiro.rickandmorty.domain.models.Character
import com.shiro.rickandmorty.domain.models.CharacterResult
import com.shiro.rickandmorty.domain.models.Pager
import com.shiro.rickandmorty.domain.repositories.CharactersRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllCharactersUseCaseTest {

    @MockK
    lateinit var charactersRepository: CharactersRepository

    @InjectMockKs
    lateinit var getAllCharactersUseCase: GetAllCharactersUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When the user requests a characters list, then the repository returns a non-empty list`() {
        runTest {

            //Given
            coEvery {
                charactersRepository.getAllCharacters(any())
            } returns flow {
                emit(Result.success(getMockResult()))
            }

            //When
            val result = getAllCharactersUseCase.invoke(1)

            //Then
            result.collect {
                it.onSuccess { characterResult ->
                    assert(!characterResult?.results.isNullOrEmpty())
                }
            }
        }
    }

    @Test
    fun `When the user requests a character list and an error occurs, then the repository throws an error`() {
        runTest {

            //Given
            coEvery {
                charactersRepository.getAllCharacters(any())
            } returns flow {
                emit(Result.failure(ApiError.Unknown()))
            }

            //When
            val result = getAllCharactersUseCase.invoke(1)

            //Then
            result.collect {
                it.onFailure { error ->
                    assert(error is ApiError.Unknown)
                }
            }
        }
    }

    private fun getMockResult(): CharacterResult {
        val characters = mutableListOf<Character>()
        repeat(5) {
            characters.add(Character())
        }
        return CharacterResult(
            info = Pager(),
            results = characters
        )
    }
}