package com.shiro.rickandmorty.data.models.character

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shiro.rickandmorty.data.models.character.location.CharacterLocationLocal
import com.shiro.rickandmorty.data.models.character.origin.CharacterOriginLocal

@Entity
data class CharacterLocal(
    @PrimaryKey
    @NonNull
    var id: Int? = null,

    var name: String? = null,
    var status: String? = null,
    var species: String? = null,
    var type: String? = null,
    var gender: String? = null,

    @Embedded
    var origin: CharacterOriginLocal? = null,

    @Embedded
    var location: CharacterLocationLocal? = null,

    var image: String? = null,
    var url: String? = null,
    var created: String? = null
)