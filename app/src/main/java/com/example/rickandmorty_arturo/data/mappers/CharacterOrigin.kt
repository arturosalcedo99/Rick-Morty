package com.example.rickandmorty_arturo.data.mappers

import com.example.rickandmorty_arturo.data.models.character.origin.CharacterOriginLocal
import com.example.rickandmorty_arturo.data.models.character.origin.CharacterOriginRemote

fun CharacterOriginLocal.toCharacterOriginRemote(): CharacterOriginRemote =
    CharacterOriginRemote(
        name = this.name,
        url = this.url
    )

fun CharacterOriginRemote.toCharacterOriginLocal(): CharacterOriginLocal =
    CharacterOriginLocal(
        name = this.name,
        url = this.url
    )