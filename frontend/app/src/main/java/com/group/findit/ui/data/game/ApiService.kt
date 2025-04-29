package com.group.findit.ui.data.game

import com.group.findit.ui.data.game.model.DetectionResponse
import com.group.findit.ui.data.game.model.ObjectResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("detection")
    suspend fun getDetection(
        @Part("word") word: RequestBody,
        @Part file: MultipartBody.Part
    ): DetectionResponse

    @GET("api/")
    suspend fun getObject(): ObjectResponse
}
