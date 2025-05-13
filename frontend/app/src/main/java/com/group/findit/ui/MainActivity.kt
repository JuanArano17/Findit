package com.group.findit.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.group.findit.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity for the FindIt app.
 * Hosts the navigation graph and initializes the app's UI.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Called when the activity is created.
     * Sets up the layout and hides the ActionBar.
     * @param savedInstanceState The saved instance state of the activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Disable the ActionBar
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}