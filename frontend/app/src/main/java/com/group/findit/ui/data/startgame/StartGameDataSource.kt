package com.group.findit.ui.data.startgame

import com.group.findit.ui.startgame.model.RemoteParticipantDto
import retrofit2.Response

/**
 * Data source interface for starting a game.
 * Provides methods to interact with the remote API for participant data.
 */
interface StartGameDataSource {
    /**
     * Fetches a participant from the remote API.
     * @return A [Response] containing a [RemoteParticipantDto].
     */
    suspend fun getParticipant(): Response<RemoteParticipantDto>
}