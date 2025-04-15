package com.group.findit.ui.game.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoDto(
    val name: String,
    val picture: Picture
)

data class Picture(val large: String, val medium: String, val thumbnail: String)
