package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class otpUserName : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_otp_user_name)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button2 = findViewById<ImageView>(R.id.close2)
        button2.setOnClickListener {
            val intent = Intent(this, confUsername::class.java)
            startActivity(intent)
        }


        val button3 = findViewById<TextView>(R.id.vertify2)
        button3.setOnClickListener {
            val intent = Intent(this, renameUser::class.java)
            startActivity(intent)


        }
    }
}