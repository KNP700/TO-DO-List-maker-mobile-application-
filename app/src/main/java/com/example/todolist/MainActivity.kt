package com.example.todolist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.services.FirebaseService
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val service = FirebaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.startBtn.setOnClickListener {
            startActivity(Intent(this, SignInPage::class.java))



            val checkPrefs = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            val isAlreadyLoggedIn = checkPrefs.getBoolean("isLoggedIn", false)

//            service.createCollection()
            if (isAlreadyLoggedIn) {
                val intent = Intent(this, HomePage::class.java)
                startActivity(intent)
                finish()
                return@setOnClickListener


            }

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)


            binding.startBtn.setOnClickListener {
                startActivity(Intent(this, SignInPage::class.java))
            }
        }
    }
}

