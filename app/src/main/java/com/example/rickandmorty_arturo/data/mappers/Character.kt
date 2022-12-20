package com.example.rickandmorty_arturo.data.mappers

import com.example.rickandmorty_arturo.data.models.character.CharacterLocal
import com.example.rickandmorty_arturo.data.models.character.CharacterRemote

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

fun List<CharacterLocal>.toCharacterRemoteList(): List<CharacterRemote> =
    this.map { it.toCharacterRemote() }

fun List<CharacterRemote>.toCharacterLocalList(): List<CharacterLocal> =
    this.map { it.toCharacterLocal() }