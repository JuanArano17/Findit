package com.group.findit.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.group.findit.R
import com.group.findit.databinding.FragmentDashboardBinding
import com.group.findit.ui.home.HomeViewModel

class DashboardFragment: Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Glide.with(this)
            .asGif()
            .load(R.drawable.download)
            .into(binding.gifBackgroundDashboard)
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_home)
        }

        val prefs = requireContext().getSharedPreferences("puntuaciones", Context.MODE_PRIVATE)
        val allScores = prefs.all  // Map<String, *>

        var idGame = arguments?.getString("IDGame") ?: "SinID"
        if(idGame == "SinID")
            idGame = arguments?.getString("IDGameSG") ?: "SinID"
        var topPlayer = ""
        var maxScore = Int.MIN_VALUE

        for ((key, value) in allScores) {
            if (value is Int && key.endsWith(":$idGame")) {
                val playerName = key.substringBefore(":")  // Extrae el nombre del jugador
                if (value > maxScore) {
                    maxScore = value
                    topPlayer = playerName
                }
            }
        }

        binding.textName.text = topPlayer
        Log.d("PUNTUACIONES", "Top player: $topPlayer con $maxScore puntos en juego $idGame")

        setupRecyclerView()
        return root
    }

    private fun setupRecyclerView() {
        val prefs = requireContext().getSharedPreferences("puntuaciones", Context.MODE_PRIVATE)
        val allScores = prefs.all  // Map<String, *>

        val idGame = arguments?.getString("IDGame") ?: return

        val filteredScores = allScores.mapNotNull { entry ->
            val key = entry.key // formato: "nombre:id"
            val score = entry.value as? Int ?: return@mapNotNull null

            val parts = key.split(":")
            if (parts.size == 2 && parts[1] == idGame) {
                val nameOnly = parts[0]
                nameOnly to score
            } else null
        }

        val sortedScores = filteredScores.sortedByDescending { it.second }
        val nombres = sortedScores.map { it.first }
        val puntajes = sortedScores.map { it.second }

        val adapter = ScoreAdapter(nombres, puntajes)
        binding.recyclerViewScores.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewScores.adapter = adapter
    }


    override fun onDestroyView() {
        val prefs = requireContext().getSharedPreferences("puntuaciones", Context.MODE_PRIVATE)
       // prefs.edit().clear().apply()
        super.onDestroyView()
        _binding = null
    }
}