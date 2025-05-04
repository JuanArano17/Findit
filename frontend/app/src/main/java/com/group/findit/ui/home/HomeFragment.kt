package com.group.findit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.group.findit.databinding.FragmentHomeBinding
import com.group.findit.R

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Cargar GIF en el ImageView con Glide
        Glide.with(this)
            .asGif()
            .load(R.drawable.download)
            .into(binding.gifBackground)

        // Configurar botones
        binding.buttonStartGame.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_start_game)
        }
        binding.buttonJoinGame.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_join_game)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
