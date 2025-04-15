package com.group.findit.ui.game

import com.group.findit.ui.domain.model.Photo

interface GameRepository {
    suspend fun postPhoto(photo: Photo):
            Result<Boolean>
}

//interface ApiService {
//    @Multipart
//    @POST("/upload") // Reemplaza con el endpoint de tu API
//    suspend fun uploadImage(@Part file: MultipartBody.Part): Response<ResponseBody>
//}