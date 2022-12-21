package com.shiro.rickandmorty.data.mappers

import com.example.rickandmorty_arturo.data.models.character.CharacterRemote
import com.shiro.rickandmorty.data.models.character.CharacterLocal
import com.shiro.rickandmorty.domain.models.Character

fun CharacterLocal.toCharacterRemote(): CharacterRemote =
    CharacterRemote(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        origin = this.origin?.toCharacterOriginRemote(),
        location = this.location?.toCharacterLocationRemote(),
        image = this.image,
        url = this.url,
        created = this.created
    )

fun CharacterRemote.toCharacterLocal(): CharacterLocal =
    CharacterLocal(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        origin = this.origin?.toCharacterOriginLocal(),
        location = this.location?.toCharacterLocationLocal(),
        image = this.image,
        url = this.url,
        created = this.created
    )

fun CharacterRemote.toDomain(): Character =
    Character(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        gender = this.gender,
        origin = this.origin?.toDomain(),
        location = this.location?.toDomain(),
        image = this.image
    )

fun CharacterLocal.toDomain(): Character =
    Character(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        gender = this.gender,
        origin = this.origin?.toDomain(),
        location = this.location?.toDomain(),
        image = this.image
    )

fun List<CharacterLocal>.toCharacterRemoteList(): List<CharacterRemote> =
    this.map { it.toCharacterRemote() }

fun List<CharacterRemote>.toCharacterLocalList(): List<CharacterLocal> =
    this.map { it.toCharacterLocal() }

@JvmName("toDomainCharacterLocal")
fun List<CharacterLocal>.toDomain(): List<Character> =
    this.map { it.toDomain() }

@JvmName("toDomainCharacterRemote")
fun List<CharacterRemote>.toDomain(): List<Character> =
    this.map { it.toDomain() }