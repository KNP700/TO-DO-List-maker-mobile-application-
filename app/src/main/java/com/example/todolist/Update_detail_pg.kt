package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Update_detail_pg : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_detail_pg)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button3 = findViewById<ImageView>(R.id.close)
        button3.setOnClickListener {
            val intent = Intent(this, User_detail::class.java)
            startActivity(intent)
        }

        val button = findViewById<Button>(R.id.Save)
        button.setOnClickListener {
            val intent = Intent(this, Home_pg::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.Cancel)
        button2.setOnClickListener {
            val intent = Intent(this, User_detail::class.java)
            startActivity(intent)
        }
    }
}