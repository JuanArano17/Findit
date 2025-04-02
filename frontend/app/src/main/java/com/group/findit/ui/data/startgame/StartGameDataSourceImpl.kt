package com.group.findit.ui.data.startgame

import com.group.findit.ui.startgame.model.RemoteParticipantDto
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class StartGameDataSourceImpl @Inject constructor(retrofit: Retrofit) : StartGameDataSource{
    private val retrofitQuotationService =
        retrofit.create(StartGameRetrofit::class.java)
    override suspend fun getParticipant(): Response<RemoteParticipantDto> {
        return try {
            retrofitQuotationService.getParticipant()
        } catch (e: Exception) {
            Response.error(
                400,
                ResponseBody.create(MediaType.parse("text/plain"), e.toString())
            )
        }
    }
}