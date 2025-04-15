package com.group.findit.ui.data.game

import com.group.findit.ui.domain.model.Photo
import retrofit2.Response
import retrofit2.http.GET

interface GameRetrofit {
    @GET("api/")
    suspend fun postPhoto(photo: Photo): Response<Boolean>
}