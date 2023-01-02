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
class GetCharactersBySearchUseCaseTest {

    @MockK
    lateinit var charactersRepository: CharactersRepository

    @InjectMockKs
    lateinit var getCharactersBySearchUseCase: GetCharactersBySearchUseCase

    private val query = "Rick"

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When the user submits a query, then the repository returns some results`() {
        runTest {

            //Given
            coEvery {
                charactersRepository.searchCharacters(any(), any())
            } returns flow {
                emit(Result.success(getMockResult()))
            }

            //When
            val result = getCharactersBySearchUseCase.invoke(query, 1)

            //Then
            result.collect {
                it.onSuccess { characterResult ->
                    assert(!characterResult?.results.isNullOrEmpty())
                }
            }
        }
    }

    @Test
    fun `When the user submits a query and an error occurs, then the repository throws an error`() {
        runTest {

            //Given
            coEvery {
                charactersRepository.searchCharacters(any(), any())
            } returns flow {
                emit(Result.failure(ApiError.NotFound()))
            }

            //When
            val result = getCharactersBySearchUseCase.invoke(query, 1)

            //Then
            result.collect {
                it.onFailure { error ->
                    assert(error is ApiError.NotFound)
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