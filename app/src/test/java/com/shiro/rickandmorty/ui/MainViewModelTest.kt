package com.shiro.rickandmorty.ui

import app.cash.turbine.test
import com.shiro.rickandmorty.data.source.remote.api.ApiError
import com.shiro.rickandmorty.domain.models.Character
import com.shiro.rickandmorty.domain.models.CharacterResult
import com.shiro.rickandmorty.domain.models.Pager
import com.shiro.rickandmorty.domain.use_cases.GetAllCharactersUseCase
import com.shiro.rickandmorty.domain.use_cases.GetCharactersBySearchUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @MockK
    lateinit var getAllCharactersUseCase: GetAllCharactersUseCase

    @MockK
    lateinit var getCharactersBySearchUseCase: GetCharactersBySearchUseCase

    @InjectMockKs
    lateinit var viewModel: MainViewModel

    private val query = "Rick"

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When the user requests a characters list, then some results are retrieved`() {
        runTest {

            //Given
            coEvery {
                getAllCharactersUseCase.invoke(any())
            } returns flow {
                emit(Result.success(getMockResult()))
            }

            //When
            viewModel.getCharacters()

            //Then
            viewModel.mainUiState.test {
                val uiState = awaitItem()
                assert(uiState.characters.isNotEmpty())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `When the user requests a characters list and the returned pager has no next, then the viewModel page is reset`() {
        runTest {

            val characterResult = getMockResult()

            //Given
            coEvery {
                getAllCharactersUseCase.invoke(any())
            } returns flow {
                emit(Result.success(characterResult))
            }

            //When
            viewModel.getCharacters()

            //Then
            viewModel.mainUiState.test {
                awaitItem()
                val uiState = awaitItem()
                assert(uiState.page == 0)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `When the user requests a characters list and the returned pager has next, then the viewModel page is increased`() {
        runTest {

            val characterResult = getMockResult(false)

            //Given
            coEvery {
                getAllCharactersUseCase.invoke(any())
            } returns flow {
                emit(Result.success(characterResult))
            }

            //When
            viewModel.getCharacters()

            //Then
            viewModel.mainUiState.test {
                awaitItem()
                val uiState = awaitItem()
                assert(uiState.page > 1)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `When the user requests a characters list and an error occurs, then the error is returned`() {
        runTest {

            //Given
            coEvery {
                getAllCharactersUseCase.invoke(any())
            } returns flow {
                emit(Result.failure(ApiError.Unknown()))
            }

            //When
            viewModel.getCharacters()

            //Then
            viewModel.mainUiState.test {
                awaitItem()
                val uiState = awaitItem()
                assert(uiState.errorMessage == ApiError.Unknown().errorMessage)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `When the user submits a query, then some results are retrieved`() {
        runTest {

            val characterResult = getMockResult()

            //Given
            coEvery {
                getCharactersBySearchUseCase.invoke(any(), any())
            } returns flow {
                emit(Result.success(characterResult))
            }

            //When
            viewModel.setSearchQuery(query, false)
            viewModel.getCharactersBySearch()

            //Then
            viewModel.mainUiState.test {
                val uiState = awaitItem()
                assert(uiState.searchQuery == query)
                assert(uiState.characters.isNotEmpty())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `When the user submits a query and an error occurs, then the error is returned`() {
        runTest {

            //Given
            coEvery {
                getCharactersBySearchUseCase.invoke(any(), any())
            } returns flow {
                emit(Result.failure(ApiError.Unknown()))
            }

            //When
            viewModel.setSearchQuery(query, false)
            viewModel.getCharactersBySearch()

            //Then
            viewModel.mainUiState.test {
                awaitItem()
                val uiState = awaitItem()
                assert(uiState.searchQuery == query)
                assert(uiState.characters.isEmpty())
                assert(uiState.errorMessage == ApiError.Unknown().errorMessage)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    private fun getMockResult(nullPager: Boolean = true): CharacterResult {
        val characters = mutableListOf<Character>()
        repeat(5) {
            characters.add(Character())
        }
        return CharacterResult(
            info = Pager().apply {
                next = if (nullPager) null else ""
            },
            results = characters
        )
    }
}