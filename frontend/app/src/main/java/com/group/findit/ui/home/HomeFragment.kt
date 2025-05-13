package com.group.findit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.group.findit.R
import com.group.findit.databinding.FragmentHomeBinding

/**
 * Fragment for the Home screen.
 * Provides navigation options to start a new game or join an existing game.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Load a GIF as the background
        Glide.with(this)
            .asGif()
            .load(R.drawable.download)
            .into(binding.gifBackground)

        // Navigate to the Start Game screen
        binding.buttonStartGame.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_start_game)
        }

        // Navigate to the Join Game screen
        binding.buttonJoinGame.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_join_game)
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
