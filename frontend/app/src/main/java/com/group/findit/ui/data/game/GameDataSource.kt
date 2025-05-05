package com.group.findit.ui.data.game

import com.group.findit.ui.domain.model.Photo
import retrofit2.Response

/**
 * Data source interface for game-related operations.
 * Provides methods to interact with the remote API for photo data.
 */
interface GameDataSource {
    /**
     * Sends a photo to the remote API.
     * @param photo The [Photo] to be sent.
     * @return A [Response] containing a [Boolean] indicating success or failure.
     */
    suspend fun postPhoto(photo: Photo): Response<Boolean>
}