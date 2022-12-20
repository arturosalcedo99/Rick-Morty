package com.example.rickandmorty_arturo.data.models.character.origin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterOriginLocal(

    @PrimaryKey
    @ColumnInfo(name = "character_origin_name")
    var name: String? = null,

    @ColumnInfo(name = "character_origin_url")
    var url: String? = null
)