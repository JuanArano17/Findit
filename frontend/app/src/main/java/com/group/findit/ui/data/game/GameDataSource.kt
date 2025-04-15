package com.group.findit.ui.data.game

import com.group.findit.ui.domain.model.Photo
import retrofit2.Response

interface GameDataSource {
    suspend fun postPhoto(photo: Photo): Response<Boolean>
}