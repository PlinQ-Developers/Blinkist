package com.blinkslabs.blinkist.android.challenge.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blinkslabs.blinkist.android.challenge.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlinkistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
