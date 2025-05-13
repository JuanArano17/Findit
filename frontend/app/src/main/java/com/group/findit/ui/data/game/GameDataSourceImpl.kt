package com.group.findit.ui.data.game

import com.group.findit.ui.domain.model.Photo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Implementation of the [GameDataSource] interface.
 * Handles API calls related to game operations.
 * @constructor Injects a [Retrofit] instance for API communication.
 */
class GameDataSourceImpl @Inject constructor(retrofit: Retrofit) : GameDataSource {
    private val retrofitPhotoDetectionService = retrofit.create(GameRetrofit::class.java)

    /**
     * Sends a photo to the API for detection.
     * @param photo The [Photo] object to be sent.
     * @return A [Response] containing a [Boolean] indicating success or failure.
     */
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
