package com.group.findit.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.group.findit.R
import com.group.findit.databinding.FragmentDashboardBinding

/**
 * Fragment for the Dashboard screen.
 * Displays the top player and a scoreboard for the current game.
 */
class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    /**
     * Inflates the layout and initializes the UI components.
     * @param inflater The LayoutInflater object that can be used to inflate views.
     * @param container The parent view that this fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The root view of the fragment's layout.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: ConstraintLayout? = binding.root

        // Load a GIF as the background
        Glide.with(this)
            .asGif()
            .load(R.drawable.download)
            .into(binding.gifBackgroundDashboard)

        // Navigate back to the home screen
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_home)
        }

        // Retrieve and display the top player for the current game
        val prefs = requireContext().getSharedPreferences("puntuaciones", Context.MODE_PRIVATE)
        val allScores = prefs.all  // Map<String, *>

        var idGame = arguments?.getString("IDGame") ?: "SinID"
        if (idGame == "SinID")
            idGame = arguments?.getString("IDGameSG") ?: "SinID"
        var topPlayer = ""
        var maxScore = Int.MIN_VALUE

        for ((key, value) in allScores) {
            if (value is Int && key.endsWith(":$idGame")) {
                val playerName = key.substringBefore(":")  // Extract the player's name
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

    /**
     * Sets up the RecyclerView to display the scoreboard.
     * Filters and sorts the scores for the current game and binds them to the RecyclerView.
     */
    private fun setupRecyclerView() {
        val prefs = requireContext().getSharedPreferences("puntuaciones", Context.MODE_PRIVATE)
        val allScores = prefs.all  // Map<String, *>

        val idGame = arguments?.getString("IDGame") ?: return

        // Filter and map scores for the current game
        val filteredScores = allScores.mapNotNull { entry ->
            val key = entry.key // formato: "nombre:id"
            val score = entry.value as? Int ?: return@mapNotNull null

            val parts = key.split(":")
            if (parts.size == 2 && parts[1] == idGame) {
                val nameOnly = parts[0]
                nameOnly to score
            } else null
        }

        // Sort scores in descending order
        val sortedScores = filteredScores.sortedByDescending { it.second }
        val nombres = sortedScores.map { it.first }
        val puntajes = sortedScores.map { it.second }

        // Bind data to the RecyclerView
        val adapter = ScoreAdapter(nombres, puntajes)
        binding.recyclerViewScores.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewScores.adapter = adapter
    }

    /**
     * Clears the shared preferences and releases the binding when the view is destroyed.
     */
    override fun onDestroyView() {
        val prefs = requireContext().getSharedPreferences("puntuaciones", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
        super.onDestroyView()
        _binding = null
    }
}