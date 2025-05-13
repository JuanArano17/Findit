package com.group.findit.ui.game

import com.group.findit.ui.domain.model.Photo

/**
 * Repository interface for game-related operations.
 * Defines the contract for posting photos to the API.
 */
interface GameRepository {

    /**
     * Sends a photo to the API for processing.
     * @param photo The [Photo] object to be sent.
     * @return A [Result] containing a [Boolean] indicating success or failure.
     */
    suspend fun postPhoto(photo: Photo): Result<Boolean>
}