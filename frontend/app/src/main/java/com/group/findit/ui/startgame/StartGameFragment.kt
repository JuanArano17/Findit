package com.group.findit.ui.startgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.group.findit.R
import com.group.findit.databinding.FragmentStartGameBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint

class StartGameFragment : Fragment() {

    private var _binding: FragmentStartGameBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val startGameViewModel =
            ViewModelProvider(this).get(StartGameViewModel::class.java)
        _binding = FragmentStartGameBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // Cargar GIF en el ImageView con Glide
        Glide.with(this)
            .asGif()
            .load(R.drawable.download) // Asegúrate de que el archivo está en res/drawable o res/raw
            .into(binding.gifBackground)

       // startGameViewModel.getNewParticipant()

     /*   viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                startGameViewModel.participant.collect { participant ->
                    binding.playerNameInput.text = participant?.name
                    binding.ageInput?.text = participant?.age.toString()
                    binding.nationalityInput.text = participant?.nat
                }
            }
        }
      */

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_start_to_navigation_home)
        }

        binding.buttonJoin.setOnClickListener {
            val nombreJugador = binding.playerNameInput.text.toString()
            val IDJuego = binding.idInput?.text.toString()
            val bundle = Bundle()
            bundle.putString("playerNameSG", nombreJugador)
            bundle.putString("IDGameSG", IDJuego)
            findNavController().navigate(R.id.action_navigation_start_to_navigation_game,bundle)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}