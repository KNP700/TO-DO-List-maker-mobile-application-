package com.example.todolist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home_pg : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_pg)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button = findViewById<ImageView>(R.id.user)
        button.setOnClickListener {
            val intent = Intent(this, User_detail::class.java)
            startActivity(intent)
        }
        val button2 = findViewById<TextView>(R.id.user2)
        button2.setOnClickListener {
            val intent = Intent(this, User_detail::class.java)
            startActivity(intent)
        }

        val button3 = findViewById<ImageView>(R.id.menu)
        button3.setOnClickListener {
            val intent = Intent(this, Menu_pg::class.java)
            startActivity(intent)
        }

        val button4 = findViewById<Button>(R.id.Add_list)
        button4.setOnClickListener {
            val intent = Intent(this, Add_list::class.java)
            startActivity(intent)
        }



    }
}