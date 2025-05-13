package com.group.findit.ui.game.model

import com.squareup.moshi.JsonClass

/**
 * Data Transfer Object (DTO) for photo data.
 * Used for mapping JSON responses to Kotlin objects.
 * @property name The name associated with the photo.
 * @property picture The picture details (large, medium, thumbnail).
 */
@JsonClass(generateAdapter = true)
data class PhotoDto(
    val name: String,
    val picture: Picture
)

/**
 * Represents the picture details.
 * @property large URL for the large version of the picture.
 * @property medium URL for the medium version of the picture.
 * @property thumbnail URL for the thumbnail version of the picture.
 */
data class Picture(val large: String, val medium: String, val thumbnail: String)
