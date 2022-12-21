package com.shiro.rickandmorty.data.models

import com.example.rickandmorty_arturo.data.models.character.CharacterRemote
import com.shiro.rickandmorty.domain.models.Pager

data class CharacterResultRemote(
    val info: Pager? = null,
    val results: List<CharacterRemote>? = null
)