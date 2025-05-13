package com.group.findit.ui.startgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.group.findit.R
import com.group.findit.databinding.FragmentStartGameBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for the Start Game screen.
 * Allows the user to create a new game by entering their name and game ID.
 */
@AndroidEntryPoint
class StartGameFragment : Fragment() {

    private var _binding: FragmentStartGameBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
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
    ): View {
        _binding = FragmentStartGameBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Load a GIF as the background
        Glide.with(this)
            .asGif()
            .load(R.drawable.download) // Ensure the file is in res/drawable or res/raw
            .into(binding.gifBackground)

        // Navigate back to the home screen
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_start_to_navigation_home)
        }

        // Navigate to the game screen with the entered player name and game ID
        binding.buttonJoin.setOnClickListener {
            val playerName = binding.playerNameInput.text.toString()
            val gameId = binding.idInput?.text.toString()
            val bundle = Bundle()
            bundle.putString("playerNameSG", playerName)
            bundle.putString("IDGameSG", gameId)
            findNavController().navigate(R.id.action_navigation_start_to_navigation_game, bundle)
        }

        return root
    }

    /**
     * Releases the binding when the view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}