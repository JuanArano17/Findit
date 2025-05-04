package com.group.findit.ui.data.game

import com.group.findit.ui.domain.model.Photo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class GameDataSourceImpl @Inject constructor(retrofit: Retrofit) : GameDataSource {
    private val retrofitPhotoDetectionService = retrofit.create(GameRetrofit::class.java)

    override suspend fun postPhoto(photo: Photo): Response<Boolean> {
        return try {
            retrofitPhotoDetectionService.postPhoto(photo)
        } catch (e: Exception) {
            Response.error(
                400,
                ResponseBody.create("text/plain".toMediaTypeOrNull(), e.toString())
            )
        }
    }
}
