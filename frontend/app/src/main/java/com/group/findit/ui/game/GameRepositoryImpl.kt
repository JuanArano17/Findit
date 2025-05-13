package com.group.findit.ui.game

import android.util.Log
import com.group.findit.ui.data.game.ConnectivityChecker
import com.group.findit.ui.data.game.GameDataSourceImpl
import com.group.findit.ui.domain.model.Photo
import com.group.findit.ui.utils.NoInternetException
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation of the [GameRepository] interface.
 * Handles the logic for posting photos to the API.
 * @constructor Injects the required dependencies.
 */
class GameRepositoryImpl @Inject constructor(
    private val source: GameDataSourceImpl,
    private val connectivityChecker: ConnectivityChecker
) : GameRepository {

    /**
     * Sends a photo to the API for processing.
     * @param photo The [Photo] object to be sent.
     * @return A [Result] containing a [Boolean] indicating success or failure.
     * @throws NoInternetException if there is no internet connection.
     */
    override suspend fun postPhoto(photo: Photo): Result<Boolean> {
        if (!connectivityChecker.isConnectionAvailable()) {
            throw NoInternetException("No hay conexión a Internet")
        }

        // Perform the API call and log the response
        val response = source.postPhoto(photo)

        // Log the full API response
        Log.d("API_RESPONSE", "Response: $response")

        // Check if the response was successful
        if (response.isSuccessful) {
            Log.d("API_RESPONSE", "Successful response: ${response.body()}")
        } else {
            Log.e("API_RESPONSE", "Error response: ${response.code()} - ${response.message()}")
        }

        // Convert the response to the domain model
        return response.toDomain()
    }
}

/**
 * Extension function to convert a [Response] to a [Result].
 * @return A [Result] containing the response body if successful, or an error if not.
 */
private fun <T> Response<T>.toDomain(): Result<T> {
    return if (isSuccessful) {
        body()?.let { Result.success(it) } ?: Result.failure(Exception("Empty response body"))
    } else {
        Result.failure(Exception("Error response: ${code()} - ${message()}"))
    }
}
