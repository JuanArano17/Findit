package com.group.findit.ui.game

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.group.findit.R
import com.group.findit.databinding.FragmentGameBinding
import com.group.findit.ui.data.game.model.ObjectResponse
import com.group.findit.ui.home.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.group.findit.ui.data.game.ApiClient
import com.group.findit.ui.data.game.model.DetectionResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * Fragment for the game screen.
 * Handles UI interactions and API calls for game-related features.
 */
class GameFragment: Fragment()  {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var cameraExecutor: ExecutorService
    private var tiempo = 45L // 30 segundos
    private val handler = android.os.Handler()
    private val _objectResponse = MutableStateFlow<ObjectResponse?>(null)
    val objectResponse: StateFlow<ObjectResponse?> = _objectResponse
    private val _detectionResponse = MutableStateFlow<DetectionResponse?>(null)
    val detectionResponse: StateFlow<DetectionResponse?> = _detectionResponse
    private lateinit var imageCapture: ImageCapture


    private val actualizador = object : Runnable {
        override fun run() {
            if (tiempo > 0) {
                tiempo--
                actualizarCronometro()
                handler.postDelayed(this, 1000) // Actualiza cada 1 segundo
            } else {
                activity?.runOnUiThread {
                    var idGame = arguments?.getString("IDGame") ?: "SinID"
                    if (idGame == "SinID")
                        idGame = arguments?.getString("IDGameSG") ?: "SinID"
                    val bundle = Bundle()
                    bundle.putString("IDGame", idGame)
                    findNavController().navigate(R.id.action_navigation_game_to_navigation_dashboard, bundle)
                }
            }
        }
    }

    fun fetchObjection() {
        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getObject()
                _objectResponse.value = response
                Log.e("API_CALL", "Respuesta de la API Objection: ${_objectResponse.value}")

                _binding?.let { safeBinding ->
                    safeBinding.textGame.text = response.word
                }
            } catch (e: Exception) {
                _objectResponse.value = null
                _binding?.let { safeBinding ->
                    safeBinding.textGame.text = "Error"
                }
                Log.e("API_CALL", "Error al llamar la API: ${e.message}", e)
            }
        }
    }

    /**
     * Fetches detection results from the API using an image.
     * @param imageUri The URI of the image to be sent.
     */
    fun fetchDetection(imageUri: Uri) {
        lifecycleScope.launch {
            try {
                val word = binding.textGame.text.toString()

                val contentResolver = requireContext().contentResolver
                val inputStream = contentResolver.openInputStream(imageUri)
                val file = File(requireContext().cacheDir, "image.jpg")
                inputStream?.use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                }

                val requestWord = word.toRequestBody("text/plain".toMediaTypeOrNull())
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                val response = ApiClient.apiService.getDetection(requestWord, body)

                _detectionResponse.value = response
                Log.e("API_CALL", "Respuesta de la API Detection: ${_detectionResponse.value}")
                val prefs = requireContext().getSharedPreferences("puntuaciones", Context.MODE_PRIVATE)
                val playerName = arguments?.getString("playerName") ?: "SinNombre"
                val idGame = arguments?.getString("IDGame") ?: "SinID"
                val playerNameSG = arguments?.getString("playerNameSG") ?: "SinNombre"
                val idGameSG = arguments?.getString("IDGameSG") ?: "SinID"
                var key = "$playerName:$idGame"
                if (playerName =="SinNombre")
                    key = "$playerNameSG:$idGameSG"
                var score = prefs.getInt(key, 0)

                if (response.detected == true) {
                    score += 10
                    binding.textScore.text = "$score"
                    fetchObjection()
                    Log.d("SCORE", "Puntos actualizados: $score")
                }
                prefs.edit().putInt(key, score).apply()
            } catch (e: Exception) {
                _objectResponse.value = null
                _binding?.let { it.textGame.text = "Error" }
                Log.e("API_CALL", "Error al llamar la API: ${e.message}", e)
            }
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        handler.post(actualizador)
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fetchObjection();
        Glide.with(this)
            .asGif()
            .load(R.drawable.download)
            .into(binding.gifBackgroundGame)

        cameraExecutor = Executors.newSingleThreadExecutor()
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
        val prefs = requireContext().getSharedPreferences("puntuaciones", Context.MODE_PRIVATE)

        binding.buttonTakePhoto.setOnClickListener {
            takePhoto()
        }
        binding.buttonExit.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_game_to_navigation_home)
        }
        val prefs2 = requireContext().getSharedPreferences("puntuaciones", Context.MODE_PRIVATE)
        val playerName = arguments?.getString("playerName") ?: "SinNombre"
        val idGame = arguments?.getString("IDGame") ?: "SinID"
        val playerNameSG = arguments?.getString("playerNameSG") ?: "SinNombre"
        val idGameSG = arguments?.getString("IDGameSG") ?: "SinID"
        var key = "$playerName:$idGame"
        if (playerName =="SinNombre")
            key = "$playerNameSG:$idGameSG"
        var score = prefs2.getInt(key, 0)
        prefs2.edit().putInt(key, score).apply()
        return root
    }

    val outputDirectory: File by lazy {
        val mediaDir = requireContext().externalMediaDirs.firstOrNull()
        File(mediaDir, "FinditApp").apply { if (!exists()) mkdirs() }
    }


    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            outputDirectory,
            "${System.currentTimeMillis()}.jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("CameraX", "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    Log.d("CameraX", "Photo saved: $savedUri")

                    fetchDetection(photoFile.toUri())
                }
            }
        )
    }


    private fun actualizarCronometro() {
        val minutos = tiempo / 60
        var segundos = tiempo % 60
        val tiempoFormateado = String.format("%02d:%02d", minutos, segundos)

        _binding?.let {
            it.textTime.text = tiempoFormateado
        } ?: run {
            Log.e("GameFragment", "Binding is null, can't update UI")
        }
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.previewView.surfaceProvider)
            }

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            try {
                cameraProvider.unbindAll()

                // Inicializa ImageCapture
                imageCapture = ImageCapture.Builder().build()

                val camera: Camera = cameraProvider.bindToLifecycle(
                    viewLifecycleOwner, cameraSelector, preview, imageCapture
                )
            } catch (e: Exception) {
                Log.e("CameraX", "Error al iniciar la cámara", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}