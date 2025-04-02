package com.group.findit.ui.data.startgame

import com.group.findit.ui.startgame.model.RemoteParticipantDto
import retrofit2.Response
import retrofit2.http.GET

interface StartGameRetrofit {
    @GET("api/")
    suspend fun getParticipant(): Response<RemoteParticipantDto>
}