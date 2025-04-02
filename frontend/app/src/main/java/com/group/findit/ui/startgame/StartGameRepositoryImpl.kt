package com.group.findit.ui.startgame

import android.util.Log
import com.group.findit.ui.data.startgame.ConnectivityChecker
import com.group.findit.ui.data.startgame.StartGameDataSourceImpl
import com.group.findit.ui.domain.model.Participant
import com.group.findit.ui.startgame.model.toDomain
import com.group.findit.ui.utils.NoInternetException
import javax.inject.Inject

class StartGameRepositoryImpl @Inject constructor(
    private val source: StartGameDataSourceImpl,
    private val connectivityChecker: ConnectivityChecker
) : StartGameRepository {

    override suspend fun getNewParticipant(): Result<Participant> {
        if (!connectivityChecker.isConnectionAvailable()) {
            throw NoInternetException("No hay conexión a Internet")
        }

        // Realiza la llamada al dataSource y agrega un log para ver la respuesta
        val response = source.getParticipant()

        // Log de la respuesta completa de la API
        Log.d("API_RESPONSE", "Response: $response")

        // Verifica si la respuesta fue exitosa o no
        if (response.isSuccessful) {
            Log.d("API_RESPONSE", "Successful response: ${response.body()}")
        } else {
            Log.e("API_RESPONSE", "Error response: ${response.code()} - ${response.message()}")
        }

        // Convertir la respuesta en dominio
        return response.toDomain()
    }
}
