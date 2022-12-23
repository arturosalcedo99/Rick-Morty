package com.shiro.rickandmorty.data.repositories.impl

import com.shiro.rickandmorty.data.mappers.toCharacterLocal
import com.shiro.rickandmorty.data.mappers.toDomain
import com.shiro.rickandmorty.data.source.local.LocalCharactersDataSource
import com.shiro.rickandmorty.data.source.remote.RemoteCharactersDataSource
import com.shiro.rickandmorty.data.source.remote.api.ApiError
import com.shiro.rickandmorty.domain.models.Character
import com.shiro.rickandmorty.domain.models.CharacterResult
import com.shiro.rickandmorty.domain.repositories.CharactersRepository
import com.shiro.rickandmorty.helpers.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val remoteCharactersDataSource: RemoteCharactersDataSource,
    private val localCharactersDataSource: LocalCharactersDataSource
): CharactersRepository {
    override suspend fun getAllCharacters(page: Int): Flow<Result<CharacterResult?>> {
        return flow {
            try {
                val result = remoteCharactersDataSource.getAllCharacters(page)
                result?.results?.forEach {
                    localCharactersDataSource.saveCharacter(it.toCharacterLocal())
                }
                emit(
                    result?.let {
                        Result.success(it.toDomain())
                    } ?: Result.failure(ApiError.NotFound())
                )
            } catch (e: Exception) {
                val error = Utils.getError(e)
                if (error is ApiError.Network) {
                    val result = localCharactersDataSource.getAllCharacters()
                    emit(
                        Result.success(
                            CharacterResult(
                                null,
                                result?.toDomain()
                            )
                        )
                    )
                } else {
                    emit(Result.failure(error))
                }
            }
        }
    }

    override suspend fun getCharacterDetails(characterId: Int): Flow<Result<Character?>> {
        return flow {
            try {
                val result = remoteCharactersDataSource.getCharacterDetails(characterId)
                emit(
                    result?.let {
                        Result.success(it.toDomain())
                    } ?: Result.failure(ApiError.NotFound())
                )
            } catch (e: Exception) {
                val error = Utils.getError(e)
                if (error is ApiError.Network) {
                    val result = localCharactersDataSource.getCharacterDetails(characterId)
                    emit(Result.success(result?.toDomain()))
                } else {
                    emit(Result.failure(error))
                }
            }
        }
    }

    override suspend fun searchCharacters(name: String, page: Int): Flow<Result<CharacterResult?>> {
        return flow {
            try {
                val result = remoteCharactersDataSource.searchCharacters(name, page)
                emit(
                    result?.let {
                        Result.success(it.toDomain())
                    } ?: Result.failure(ApiError.NotFound())
                )
            } catch (e: Exception) {
                val error = Utils.getError(e)
                if (error is ApiError.Network) {
                    val result = localCharactersDataSource.searchCharacters(name)
                    emit(
                        Result.success(
                            CharacterResult(
                                null,
                                result?.toDomain()
                            )
                        )
                    )
                } else {
                    emit(Result.failure(error))
                }
            }
        }
    }
}