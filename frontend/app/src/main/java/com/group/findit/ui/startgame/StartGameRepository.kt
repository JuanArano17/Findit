package com.group.findit.ui.startgame

import com.group.findit.ui.domain.model.Participant

/**
 * Repository interface for start game-related operations.
 * Defines the contract for fetching a new participant from the API.
 */
interface StartGameRepository {

    /**
     * Fetches a new participant from the API.
     * @return A [Result] containing a [Participant] if successful, or an error if not.
     */
    suspend fun getNewParticipant(): Result<Participant>
}