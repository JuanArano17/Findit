package com.group.findit.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}