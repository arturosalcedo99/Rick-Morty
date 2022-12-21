package com.shiro.rickandmorty.domain.models

data class CharacterResult(
    val info: Pager? = null,
    val results: List<Character>? = null
)
