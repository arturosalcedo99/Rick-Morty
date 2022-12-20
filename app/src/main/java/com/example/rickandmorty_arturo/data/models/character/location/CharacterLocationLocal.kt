package com.example.rickandmorty_arturo.data.models.character.location

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class CharacterLocationLocal(
    @PrimaryKey
    @ColumnInfo(name = "character_location_name")
    var name: String? = null,

    @ColumnInfo(name = "character_location_url")
    var url: String? = null
)