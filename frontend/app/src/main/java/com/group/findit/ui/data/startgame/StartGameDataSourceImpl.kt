package com.group.findit.ui.data.startgame

import com.group.findit.ui.startgame.model.RemoteParticipantDto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Implementation of the [StartGameDataSource] interface.
 * Handles API calls related to starting a game.
 * @constructor Injects a [Retrofit] instance for API communication.
 */
class StartGameDataSourceImpl @Inject constructor(retrofit: Retrofit) : StartGameDataSource {
    private val retrofitQuotationService = retrofit.create(StartGameRetrofit::class.java)

    /**
     * Fetches a participant from the API.
     * @return A [Response] containing a [RemoteParticipantDto].
     */
    override suspend fun getParticipant(): Response<RemoteParticipantDto> {
        return try {
            retrofitQuotationService.getParticipant()
        } catch (e: Exception) {
            Response.error(
                400,
                ResponseBody.create("text/plain".toMediaTypeOrNull(), e.toString())
            )
        }
    }
}
