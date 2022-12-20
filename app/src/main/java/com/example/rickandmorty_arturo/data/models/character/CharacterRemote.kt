package com.example.rickandmorty_arturo.data.models.character

import com.example.rickandmorty_arturo.data.models.character.location.CharacterLocationRemote
import com.example.rickandmorty_arturo.data.models.character.origin.CharacterOriginRemote

data class CharacterRemote(
    val id: Int? = null,
    val name: String? = null,
    val status: String? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: String? = null,
    val origin: CharacterOriginRemote? = null,
    val location: CharacterLocationRemote? = null,
    val image: String? = null,
    val url: String? = null,
    val created: String? = null
)