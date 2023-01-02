package com.shiro.rickandmorty.data.source.remote

import com.example.rickandmorty_arturo.data.models.character.CharacterRemote
import com.shiro.rickandmorty.data.models.CharacterResultRemote
import com.shiro.rickandmorty.data.source.remote.api.CharactersApi
import com.shiro.rickandmorty.data.source.remote.impl.RemoteCharactersDataSourceImpl
import com.shiro.rickandmorty.domain.models.Pager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteCharactersDataSourceTest {

    @MockK
    lateinit var charactersApi: CharactersApi

    @InjectMockKs
    lateinit var remoteCharactersDataSource: RemoteCharactersDataSourceImpl

    private val query = "Morty"

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When the user requests a characters list, then some results are retrieved`() {
        runTest {

            //Given
            coEvery {
                charactersApi.getCharacters(any())
            } returns Response.success(200, getMockResult())

            //When
            val result = remoteCharactersDataSource.getAllCharacters(1)

            //Then
            assert(!result?.results.isNullOrEmpty())
        }
    }

    @Test
    fun `When the user requests a characters list and an error occurs, then null is returned`() {
        runTest {

            //Given
            coEvery {
                charactersApi.getCharacters(any())
            } returns Response.error(404, "".toResponseBody())

            //When
            val result = remoteCharactersDataSource.getAllCharacters(1)

            //Then
            assert(result == null)
        }
    }

    @Test
    fun `When the user submits a query, then some results are retrieved`() {
        runTest {

            //Given
            coEvery {
                charactersApi.searchCharacters(any(), any())
            } returns Response.success(200, getMockResult())

            //When
            val result = remoteCharactersDataSource.searchCharacters(query, 1)

            //Then
            assert(!result?.results.isNullOrEmpty())
        }
    }

    @Test
    fun `When the user submits a query and an error occurs, then null is retrieved`() {
        runTest {

            //Given
            coEvery {
                charactersApi.searchCharacters(any(), any())
            } returns Response.error(404, "".toResponseBody())

            //When
            val result = remoteCharactersDataSource.searchCharacters(query, 1)

            //Then
            assert(result == null)
        }
    }

    private fun getMockResult(): CharacterResultRemote {
        val characters = mutableListOf<CharacterRemote>()
        repeat(5) {
            characters.add(CharacterRemote())
        }
        return CharacterResultRemote(
            info = Pager(),
            results = characters
        )
    }
}