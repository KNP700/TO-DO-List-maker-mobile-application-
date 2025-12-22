package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class renameUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rename_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button2 = findViewById<ImageView>(R.id.close2)
        button2.setOnClickListener {
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }

        val button1 = findViewById<TextView>(R.id.update)
        button1.setOnClickListener {
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }
    }
}