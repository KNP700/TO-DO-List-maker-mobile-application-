package com.example.todolist

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()


        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.startBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))


            val checkPrefs = getSharedPreferences("UserPreferences", MODE_PRIVATE)
            val isAlreadyLoggedIn = checkPrefs.getBoolean("isLoggedIn", false)


            if (isAlreadyLoggedIn) {
                val intent = Intent(this, Home_pg::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

//        setContentView(R.layout.activity_main2)

                binding = ActivityMain2Binding.inflate(layoutInflater)
                setContentView(binding.root)


                binding.startBtn.setOnClickListener {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }
    }
}

