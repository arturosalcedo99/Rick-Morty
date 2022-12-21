package com.shiro.rickandmorty.data.mappers

import com.example.rickandmorty_arturo.data.models.character.origin.CharacterOriginRemote
import com.shiro.rickandmorty.data.models.character.origin.CharacterOriginLocal

fun CharacterOriginRemote.toCharacterOriginLocal(): CharacterOriginLocal =
    CharacterOriginLocal(
        originName = this.name ?: "",
        originUrl = this.url
    )

fun CharacterOriginLocal.toCharacterOriginRemote(): CharacterOriginRemote =
    CharacterOriginRemote(
        name = this.originName,
        url = this.originUrl
    )

fun CharacterOriginRemote.toDomain(): String = this.name ?: ""
fun CharacterOriginLocal.toDomain(): String = this.originName