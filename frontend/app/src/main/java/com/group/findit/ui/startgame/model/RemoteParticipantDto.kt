package com.group.findit.ui.startgame.model

import com.squareup.moshi.JsonClass

/**
 * Data Transfer Object (DTO) for remote participant data.
 * Used for mapping JSON responses to Kotlin objects.
 */
@JsonClass(generateAdapter = true)
data class RemoteParticipantDto(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val dob: Dob,
    val phone: String,
    val cell: String,
    val id: Id,
    val picture: Picture,
    val nat: String
)

/**
 * Represents the name details of a participant.
 * @property title The title (e.g., Mr., Ms.).
 * @property first The first name.
 * @property last The last name.
 */
data class Name(val title: String, val first: String, val last: String)

/**
 * Represents the location details of a participant.
 * @property street The street address.
 * @property city The city name.
 * @property state The state name.
 * @property country The country name.
 * @property postcode The postal code.
 * @property coordinates The geographical coordinates.
 * @property timezone The timezone information.
 */
data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val coordinates: Coordinates,
    val timezone: Timezone
)

/**
 * Represents the street details.
 * @property number The street number.
 * @property name The street name.
 */
data class Street(val number: Int, val name: String)

/**
 * Represents geographical coordinates.
 * @property latitude The latitude value.
 * @property longitude The longitude value.
 */
data class Coordinates(val latitude: String, val longitude: String)

/**
 * Represents timezone information.
 * @property offset The timezone offset.
 * @property description The timezone description.
 */
data class Timezone(val offset: String, val description: String)

/**
 * Represents login details.
 * @property uuid The unique identifier.
 * @property username The username.
 */
data class Login(val uuid: String, val username: String)

/**
 * Represents date of birth details.
 * @property date The date of birth.
 * @property age The age.
 */
data class Dob(val date: String, val age: Int)

/**
 * Represents identification details.
 * @property name The ID name.
 * @property value The ID value.
 */
data class Id(val name: String, val value: String)

/**
 * Represents picture details.
 * @property large URL for the large version of the picture.
 * @property medium URL for the medium version of the picture.
 * @property thumbnail URL for the thumbnail version of the picture.
 */
data class Picture(val large: String, val medium: String, val thumbnail: String)
