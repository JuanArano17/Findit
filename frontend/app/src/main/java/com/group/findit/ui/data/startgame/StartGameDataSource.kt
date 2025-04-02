package com.group.findit.ui.data.startgame

import com.group.findit.ui.startgame.model.RemoteParticipantDto
import retrofit2.Response

interface StartGameDataSource {
    suspend fun getParticipant(): Response<RemoteParticipantDto>
}