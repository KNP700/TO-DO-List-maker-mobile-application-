package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.todolist.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var backPressedOnce = false

    override fun onStart() {
        super.onStart()
        // Check if user is already signed in; if so, skip login and go to Home
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, Home_pg::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Handle Window Insets (Edge-to-Edge UI)
        ViewCompat.setOnApplyWindowInsetsListener(binding.viewLoging) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            v.updatePadding(
                left = bars.left,
                top = bars.top,
                right = bars.right,
                bottom = bars.bottom,
            )
            WindowInsetsCompat.CONSUMED
        }

        // Handle Double Back Press to Exit
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedOnce) {
                    finishAffinity()
                    return
                }
                backPressedOnce = true
                Toast.makeText(this@MainActivity, "Press back again to exit", Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    backPressedOnce = false
                }, 2000)
            }
        })

        // --- BUTTON ACTIONS ---

        // 1. Forgot Password Button
        binding.button.setOnClickListener {
            val intent = Intent(this, Forgot_Pass::class.java)
            startActivity(intent)
        }

        // 2. Sign Up Button
        binding.signup.setOnClickListener {
            val intent = Intent(this, SignUp_pg::class.java)
            startActivity(intent)
        }

        // 3. Login Button (Firebase Logic)
        binding.button2.setOnClickListener {
            val email = binding.UsernameBox.text.toString().trim()
            val password = binding.passwordBox.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Firebase Login Call
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Login Success
                            Log.d("MainActivity", "signInWithEmail:success")
                            Toast.makeText(baseContext, "Login Successful.", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, Home_pg::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Login Failed
                            Log.w("MainActivity", "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        Log.d("MainActivity", "onCreate() called")
    }
}