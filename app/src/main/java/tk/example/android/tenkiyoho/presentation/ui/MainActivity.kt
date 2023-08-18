package tk.example.android.tenkiyoho.presentation.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import dagger.hilt.android.AndroidEntryPoint
import tk.example.android.tenkiyoho.R
import tk.example.android.tenkiyoho.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val window: Window = window
        window.statusBarColor = Color.BLACK

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}