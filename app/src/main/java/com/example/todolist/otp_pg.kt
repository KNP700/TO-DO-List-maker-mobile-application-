package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class otp_pg : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_otp_pg)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val button = findViewById<ImageView>(R.id.close2)
        button.setOnClickListener {
            val intent = Intent(this, Forgot_Pass::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<TextView>(R.id.Resend)
        button2.setOnClickListener {
            val intent = Intent(this, Forgot_Pass::class.java)
            startActivity(intent)

        }

        val button3 = findViewById<Button>(R.id.vertify2)
        button3.setOnClickListener {
            val intent = Intent(this, Password_confirm::class.java)
            startActivity(intent)
        }
    }
}