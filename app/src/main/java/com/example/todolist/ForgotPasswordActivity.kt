package com.example.todolist

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todolist.databinding.ForgotPasswordPageBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ForgotPasswordPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ForgotPasswordPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle Close Button
        binding.close.setOnClickListener {
            finish() // Closes the activity and goes back
        }

        // Handle Reset Button
        binding.reset.setOnClickListener {
            // TODO: Implement reset password logic here
        }
    }
}