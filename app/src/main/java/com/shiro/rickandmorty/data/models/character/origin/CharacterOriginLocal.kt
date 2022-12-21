package com.shiro.rickandmorty.data.models.character.origin

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterOriginLocal(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "character_origin_name")
    var originName: String = "",

    @ColumnInfo(name = "character_origin_url")
    var originUrl: String? = null
)