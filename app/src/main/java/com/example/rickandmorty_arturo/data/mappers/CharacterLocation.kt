package com.example.rickandmorty_arturo.data.mappers

import com.example.rickandmorty_arturo.data.models.character.location.CharacterLocationLocal
import com.example.rickandmorty_arturo.data.models.character.location.CharacterLocationRemote

fun CharacterLocationLocal.toCharacterLocationRemote(): CharacterLocationRemote =
    CharacterLocationRemote(
        name = this.name,
        url = this.url
    )

fun CharacterLocationRemote.toCharacterLocationLocal(): CharacterLocationLocal =
    CharacterLocationLocal(
        name = this.name,
        url = this.url
    )