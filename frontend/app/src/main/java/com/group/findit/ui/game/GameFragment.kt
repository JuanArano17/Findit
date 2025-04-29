package com.group.findit.ui.game

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
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
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.logging.Handler
import androidx.lifecycle.viewModelScope
import com.group.findit.ui.data.game.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameFragment: Fragment()  {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var cameraExecutor: ExecutorService
    private var tiempo = 20L // 30 segundos
    private val handler = android.os.Handler()
    private val _objectResponse = MutableStateFlow<ObjectResponse?>(null)
    val objectResponse: StateFlow<ObjectResponse?> = _objectResponse

    private val actualizador = object : Runnable {
        override fun run() {
            if (tiempo > 0) {
                tiempo--
                actualizarCronometro()
                handler.postDelayed(this, 1000) // Actualiza cada 1 segundo
            } else {
                activity?.runOnUiThread {
                    findNavController().navigate(R.id.action_navigation_game_to_navigation_dashboard)
                }
            }
        }
    }

    fun fetchDetection() {
        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getObject()
                _objectResponse.value = response
                Log.e("API_CALL", "Error al llamar la API: ${_objectResponse.value}")

                binding.textGame.text = response.word
            } catch (e: Exception) {
                _objectResponse.value = null
                binding.textGame.text = "Error"
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

        fetchDetection();
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

        // Configurar botones
        binding.buttonTakePhoto.setOnClickListener {
            takePhoto()
        }
        binding.buttonExit.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_game_to_navigation_home)
        }


        return root
    }

    private fun takePhoto() {
        // Lógica para tomar una foto

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
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build()

            try {
                cameraProvider.unbindAll()
                val camera: Camera = cameraProvider.bindToLifecycle(
                    viewLifecycleOwner, cameraSelector, preview
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