package com.example.rickandmorty_arturo.data.repositories.impl

import com.example.rickandmorty_arturo.data.mappers.toCharacterLocal
import com.example.rickandmorty_arturo.data.models.CharacterResult
import com.example.rickandmorty_arturo.data.models.character.CharacterRemote
import com.example.rickandmorty_arturo.domain.repositories.CharactersRepository
import com.example.rickandmorty_arturo.data.source.remote.api.ApiError
import com.example.rickandmorty_arturo.domain.data_sources.LocalCharactersDataSource
import com.example.rickandmorty_arturo.domain.data_sources.RemoteCharactersDataSource
import com.example.rickandmorty_arturo.helpers.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val remoteCharactersDataSource: RemoteCharactersDataSource,
    private val localCharactersDataSource: LocalCharactersDataSource
): CharactersRepository {
    override suspend fun getCharacters(page: Int): Flow<Result<CharacterResult?>> {
        return flow {
            try {
                val result = remoteCharactersDataSource.getCharacters(page)
                result?.results?.onEach {
                    localCharactersDataSource.insertCharacter(it.toCharacterLocal())
                }
                emit(Result.success(result))
            } catch (e: Exception) {
                val error: ApiError = Utils.getError(e)
                if (error is ApiError.Network) {
                    val result = localCharactersDataSource.getCharacters(page)
                    emit(Result.success(CharacterResult(results = result)))
                } else emit(Result.failure(error))
            }
        }
    }

    override suspend fun getCharacterDetails(characterId: Int): Flow<Result<CharacterRemote?>> {
        return flow {
            try {
                val result = remoteCharactersDataSource.getCharacterDetails(characterId)
                emit(Result.success(result))
            } catch (e: Exception) {
                val error = Utils.getError(e)
                if (error is ApiError.Network) {
                    val result = localCharactersDataSource.getCharacterDetails(characterId)
                    emit(Result.success(result))
                } else emit(Result.failure(error))
            }
        }
    }

    override suspend fun searchCharacters(name: String, page: Int): Flow<Result<CharacterResult?>> {
        return flow {
            try {
                val result = remoteCharactersDataSource.searchCharacters(name, page)
                emit(Result.success(result))
            } catch (e: Exception) {
                val error = Utils.getError(e)
                if (error is ApiError.Network) {
                    val result = localCharactersDataSource.searchCharacters(name)
                    emit(
                        Result.success(
                            CharacterResult(results = result)
                        )
                    )
                } else emit(Result.failure(error))
            }
        }
    }
}