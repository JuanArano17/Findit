package com.group.findit.ui.data.startgame

import com.group.findit.ui.startgame.model.RemoteParticipantDto
import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit interface for starting a game.
 * Defines API endpoints for participant-related operations.
 */
interface StartGameRetrofit {
    /**
     * Fetches a participant from the API.
     * @return A [Response] containing a [RemoteParticipantDto].
     */
    @GET("api/")
    suspend fun getParticipant(): Response<RemoteParticipantDto>
}