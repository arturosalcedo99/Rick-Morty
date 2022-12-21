package com.shiro.rickandmorty.data.mappers

import com.shiro.rickandmorty.data.models.CharacterResultRemote
import com.shiro.rickandmorty.domain.models.CharacterResult

fun CharacterResultRemote.toDomain(): CharacterResult =
    CharacterResult(
        info = this.info,
        results = results?.map { it.toDomain() }
    )