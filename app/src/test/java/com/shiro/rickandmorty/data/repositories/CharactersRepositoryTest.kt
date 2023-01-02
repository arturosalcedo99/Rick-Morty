package com.shiro.rickandmorty.data.repositories

import com.example.rickandmorty_arturo.data.models.character.CharacterRemote
import com.shiro.rickandmorty.data.models.CharacterResultRemote
import com.shiro.rickandmorty.data.models.character.CharacterLocal
import com.shiro.rickandmorty.data.repositories.impl.CharactersRepositoryImpl
import com.shiro.rickandmorty.data.source.local.LocalCharactersDataSource
import com.shiro.rickandmorty.data.source.remote.RemoteCharactersDataSource
import com.shiro.rickandmorty.data.source.remote.api.ApiError
import com.shiro.rickandmorty.domain.models.Character
import com.shiro.rickandmorty.domain.models.CharacterResult
import com.shiro.rickandmorty.domain.models.Pager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersRepositoryTest {

    @MockK
    lateinit var remoteCharactersDataSource: RemoteCharactersDataSource

    @MockK
    lateinit var localCharactersDataSource: LocalCharactersDataSource

    @InjectMockKs
    lateinit var charactersRepository: CharactersRepositoryImpl

    private val query = "Summer"

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When the user requests a characters list, then the remote data source returns some results`() {
        runTest {

            //Given
            coEvery {
                remoteCharactersDataSource.getAllCharacters(any())
            } returns getMockResultRemote()

            //When
            val result = charactersRepository.getAllCharacters(1)

            //Then
            result.collect {
                it.onSuccess { characterResult ->
                    assert(!characterResult?.results.isNullOrEmpty())
                }
            }
        }
    }

    @Test
    fun `When the user requests a characters list and there's not network connection, then local data source returns some results`() {
        runTest {

            //Given
            coEvery {
                remoteCharactersDataSource.getAllCharacters(any())
            } throws ApiError.Network()

            coEvery {
                localCharactersDataSource.getAllCharacters()
            } returns getMockResultLocal()

            //When
            val result = charactersRepository.getAllCharacters(1)

            //Then
            result.collect {
                it.onSuccess { characterResult ->
                    assert(!characterResult?.results.isNullOrEmpty())
                }
            }
        }
    }

    @Test
    fun `When the user requests a characters list and an error occurs, then the error is thrown`() {
        runTest {

            //Given
            coEvery {
                remoteCharactersDataSource.getAllCharacters(any())
            } throws ApiError.Unknown()

            //When
            val result = charactersRepository.getAllCharacters(1)

            //Then
            result.collect {
                it.onFailure { error ->
                    assert(error is ApiError.Unknown)
                }
            }
        }
    }

    @Test
    fun `When the user submits a query, then some results are retrieved`() {
        runTest {

            //Given
            coEvery {
                remoteCharactersDataSource.searchCharacters(any(), any())
            } returns getMockResultRemote()

            //When
            val result = charactersRepository.searchCharacters(query, 1)

            //Then
            result.collect {
                it.onSuccess { characterResult ->
                    assert(!characterResult?.results.isNullOrEmpty())
                }
            }
        }
    }

    @Test
    fun `When the user submits a query and there's no network connection, then local data source returns some results`() {
        runTest {

            //Given
            coEvery {
                remoteCharactersDataSource.searchCharacters(any(), any())
            } throws ApiError.Network()

            coEvery {
                localCharactersDataSource.searchCharacters(any())
            } returns getMockResultLocal()

            //When
            val result = charactersRepository.searchCharacters(query, 1)

            //Then
            result.collect {
                it.onSuccess { characterResult ->
                    assert(!characterResult?.results.isNullOrEmpty())
                }
            }
        }
    }

    @Test
    fun `When the user submit a query and an error occurs, then the error is thrown`() {
        runTest {

            //Given
            coEvery {
                remoteCharactersDataSource.searchCharacters(any(), any())
            } throws ApiError.Unknown()

            //When
            val result = charactersRepository.searchCharacters(query, 1)

            //Then
            result.collect {
                it.onFailure { error ->
                    assert(error is ApiError.Unknown)
                }
            }
        }
    }

    private fun getMockResultRemote(): CharacterResultRemote {
        val characters = mutableListOf<CharacterRemote>()
        repeat(5) {
            characters.add(CharacterRemote())
        }
        return CharacterResultRemote(
            info = Pager(),
            results = characters
        )
    }

    private fun getMockResultLocal(): List<CharacterLocal> {
        val characters = mutableListOf<CharacterLocal>()
        repeat(5) {
            characters.add(CharacterLocal())
        }
        return characters
    }
}