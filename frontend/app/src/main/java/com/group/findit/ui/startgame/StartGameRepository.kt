package com.group.findit.ui.startgame

import com.group.findit.ui.domain.model.Participant

interface StartGameRepository {
    suspend fun getNewParticipant():
            Result<Participant>
}