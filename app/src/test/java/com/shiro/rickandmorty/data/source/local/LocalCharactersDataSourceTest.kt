package com.shiro.rickandmorty.data.source.local

import com.shiro.rickandmorty.data.models.character.CharacterLocal
import com.shiro.rickandmorty.data.source.local.database.CharactersDao
import com.shiro.rickandmorty.data.source.local.impl.LocalCharactersDataSourceImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LocalCharactersDataSourceTest {

    @MockK
    lateinit var charactersDao: CharactersDao

    @InjectMockKs
    lateinit var localCharactersDataSource: LocalCharactersDataSourceImpl

    private val query = "Alien"

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When the user requests a characters list, then some results are retrieved from the database`() {
        runTest {

            //Given
            coEvery {
                charactersDao.getAllCharacters()
            } returns getMockResult()

            //When
            val result = localCharactersDataSource.getAllCharacters()

            //Then
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun `When the user requests a characters list and the database is empty, then an empty list is returned`() {
        runTest {

            //Given
            coEvery {
                charactersDao.getAllCharacters()
            } returns listOf()

            //When
            val result = localCharactersDataSource.getAllCharacters()

            //Then
            assert(result.isEmpty())
        }
    }

    @Test
    fun `When the user submits a query, then some results are retrieved from the database`() {
        runTest {

            //Given
            coEvery {
                charactersDao.searchCharacters(any())
            } returns getMockResult()

            //When
            val result = localCharactersDataSource.searchCharacters(query)

            //Then
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun `When the user submits a query and no result matches, then an empty list is returned`() {
        runTest {

            //Given
            coEvery {
                charactersDao.searchCharacters(any())
            } returns listOf()

            //When
            val result = localCharactersDataSource.searchCharacters(query)

            //Then
            assert(result.isEmpty())
        }
    }

    private fun getMockResult(): List<CharacterLocal> {
        val characters = mutableListOf<CharacterLocal>()
        repeat(5) {
            characters.add(CharacterLocal())
        }
        return characters
    }
}