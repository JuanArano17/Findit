package com.group.findit.ui.joingame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.group.findit.R
import com.group.findit.databinding.FragmentJoinGameBinding

class JoinGameFragment : Fragment() {

    private var _binding: FragmentJoinGameBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val joinGameViewModel =
            ViewModelProvider(this).get(JoinGameViewModel::class.java)

        _binding = FragmentJoinGameBinding.inflate(inflater, container, false)
        val root: View = binding.root
        Glide.with(this)
            .asGif()
            .load(R.drawable.download)
            .into(binding.gifBackgroundJoin)

        binding.backButtonJoin.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_join_to_navigation_home)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}