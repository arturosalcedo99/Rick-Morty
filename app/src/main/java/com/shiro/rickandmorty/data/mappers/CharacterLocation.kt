package com.shiro.rickandmorty.data.mappers

import com.shiro.rickandmorty.data.models.character.location.CharacterLocationRemote
import com.shiro.rickandmorty.data.models.character.location.CharacterLocationLocal

fun CharacterLocationRemote.toCharacterLocationLocal(): CharacterLocationLocal =
    CharacterLocationLocal(
        locationName = this.name ?: "",
        locationUrl = this.url
    )

fun CharacterLocationLocal.toCharacterLocationRemote(): CharacterLocationRemote =
    CharacterLocationRemote(
        name = this.locationName,
        url = this.locationUrl
    )

fun CharacterLocationRemote.toDomain(): String = this.name ?: ""
fun CharacterLocationLocal.toDomain(): String = this.locationName