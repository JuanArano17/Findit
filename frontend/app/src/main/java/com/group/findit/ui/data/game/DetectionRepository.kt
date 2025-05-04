package com.group.findit.ui.data.game

import com.group.findit.ui.data.game.model.DetectionResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class DetectionRepository(private val apiService: ApiService) {

    suspend fun getDetection(word: String, file: File): DetectionResponse {
        val wordRequestBody = word.toRequestBody("text/plain".toMediaTypeOrNull())
        val fileRequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)
        return apiService.getDetection(wordRequestBody, filePart)
    }
}


