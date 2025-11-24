package com.example.todolist

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityMain2Binding
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        setContentView(R.layout.activity_main)

//        binding.button.setOnClickListener {
//            startActivity(Intent(this, MainActivity3::class.java))
//        }
    }
}










