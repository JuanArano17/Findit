package com.group.findit.ui.data.game

import com.group.findit.ui.data.game.model.DetectionResponse
import com.group.findit.ui.data.game.model.ObjectResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class DetectionRepository(private val apiService: ApiService) {

    suspend fun getDetection(word: String, file: File): DetectionResponse {
        val wordRequestBody = RequestBody.create(MediaType.parse("text/plain"), word)
        val fileRequestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)
        val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)

        return apiService.getDetection(wordRequestBody, filePart)
    }
}
