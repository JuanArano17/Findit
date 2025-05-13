package com.group.findit.ui.data.game

import com.group.findit.ui.data.game.model.DetectionResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

/**
 * Repository for handling detection-related operations.
 * Communicates with the [ApiService] to perform API calls.
 */
class DetectionRepository(private val apiService: ApiService) {

    /**
     * Sends a detection request to the API with a word and an image file.
     * @param word The word to be sent.
     * @param file The image file to be sent.
     * @return A [DetectionResponse] containing the detection result.
     */
    suspend fun getDetection(word: String, file: File): DetectionResponse {
        val wordRequestBody = word.toRequestBody("text/plain".toMediaTypeOrNull())
        val fileRequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)
        return apiService.getDetection(wordRequestBody, filePart)
    }
}


