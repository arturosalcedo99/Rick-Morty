package com.shiro.rickandmorty.data.models.character.location

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterLocationLocal(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "character_location_name")
    var locationName: String = "",

    @ColumnInfo(name = "character_location_url")
    var locationUrl: String? = null
)