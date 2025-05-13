package com.group.findit.ui.startgame

import android.util.Log
import com.group.findit.ui.data.startgame.ConnectivityChecker
import com.group.findit.ui.data.startgame.StartGameDataSourceImpl
import com.group.findit.ui.domain.model.Participant
import com.group.findit.ui.startgame.model.toDomain
import com.group.findit.ui.utils.NoInternetException
import javax.inject.Inject

/**
 * Implementation of the [StartGameRepository] interface.
 * Handles the logic for fetching a new participant from the API.
 * @constructor Injects the required dependencies.
 */
class StartGameRepositoryImpl @Inject constructor(
    private val source: StartGameDataSourceImpl,
    private val connectivityChecker: ConnectivityChecker
) : StartGameRepository {

    /**
     * Fetches a new participant from the API.
     * @return A [Result] containing a [Participant] if successful, or an error if not.
     * @throws NoInternetException if there is no internet connection.
     */
    override suspend fun getNewParticipant(): Result<Participant> {
        if (!connectivityChecker.isConnectionAvailable()) {
            throw NoInternetException("No hay conexión a Internet")
        }

        // Perform the API call and log the response
        val response = source.getParticipant()

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
