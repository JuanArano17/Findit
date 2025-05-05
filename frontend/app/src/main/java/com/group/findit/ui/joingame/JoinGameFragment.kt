package com.group.findit.ui.joingame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.group.findit.R
import com.group.findit.databinding.FragmentJoinGameBinding

/**
 * Fragment for the Join Game screen.
 * Allows the user to join an existing game by entering their name and game ID.
 */
class JoinGameFragment : Fragment() {

    private var _binding: FragmentJoinGameBinding? = null
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
        _binding = FragmentJoinGameBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Load a GIF as the background
        Glide.with(this)
            .asGif()
            .load(R.drawable.download)
            .into(binding.gifBackground)

        // Navigate back to the home screen
        binding.backButtonJoin.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_join_to_navigation_home)
        }

        binding.buttonJoin.setOnClickListener {
            val nombreJugador = binding.playerName.text.toString()
            val iDJuego = binding.gameId.text.toString()
            val bundle = Bundle()
            bundle.putString("playerName", nombreJugador)
            bundle.putString("IDGame", iDJuego)
            findNavController().navigate(R.id.action_navigation_join_to_navigation_game, bundle)
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