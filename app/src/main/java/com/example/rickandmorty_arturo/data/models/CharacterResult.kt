package com.example.rickandmorty_arturo.data.models

import com.example.rickandmorty_arturo.data.models.character.CharacterRemote
import com.example.rickandmorty_arturo.data.source.remote.api.Pager

data class CharacterResult(
    val info: Pager? = null,
    val results: List<CharacterRemote>? = null
)