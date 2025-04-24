package com.group.findit.ui.game

import com.group.findit.ui.game.GameRepository
import android.util.Log
import com.group.findit.ui.data.game.ConnectivityChecker
import com.group.findit.ui.data.game.GameDataSourceImpl
import com.group.findit.ui.domain.model.Photo
import com.group.findit.ui.game.model.toDomain
import com.group.findit.ui.utils.NoInternetException
import retrofit2.Response
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val source: GameDataSourceImpl,
    private val connectivityChecker: ConnectivityChecker
) : GameRepository {

    override suspend fun postPhoto(photo: Photo): Result<Boolean> {
        if (!connectivityChecker.isConnectionAvailable()) {
            throw NoInternetException("No hay conexión a Internet")
        }

        // Realiza la llamada al dataSource y agrega un log para ver la respuesta
        val response = source.postPhoto(photo)

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

private fun <T> Response<T>.toDomain(): Result<T> {
    TODO("Not yet implemented")
}
