package com.group.findit.ui.data.game

import com.group.findit.ui.domain.model.Photo
import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit interface for game-related operations.
 * Defines API endpoints for photo-related operations.
 */
interface GameRetrofit {
    /**
     * Sends a photo to the API.
     * @param photo The [Photo] to be sent.
     * @return A [Response] containing a [Boolean] indicating success or failure.
     */
    @GET("api/")
    suspend fun postPhoto(photo: Photo): Response<Boolean>
}