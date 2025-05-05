package com.group.findit.ui.data.game

import com.group.findit.ui.data.game.model.DetectionResponse
import com.group.findit.ui.data.game.model.ObjectResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Retrofit service interface for game-related API operations.
 */
interface ApiService {

    /**
     * Sends a detection request to the API with a word and an image file.
     * @param word The word to be sent as a [RequestBody].
     * @param file The image file to be sent as a [MultipartBody.Part].
     * @return A [DetectionResponse] containing the detection result.
     */
    @Multipart
    @POST("detection")
    suspend fun getDetection(
        @Part("word") word: RequestBody,
        @Part file: MultipartBody.Part
    ): DetectionResponse

    /**
     * Fetches an object from the API.
     * @return An [ObjectResponse] containing the object data.
     */
    @GET("object")
    suspend fun getObject(): ObjectResponse
}
