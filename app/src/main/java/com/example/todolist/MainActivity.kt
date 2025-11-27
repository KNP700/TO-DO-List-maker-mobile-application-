package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener  {
            val intent = Intent(this, Forgot_Pass::class.java)
            startActivity(intent)
        }
        val button2 = findViewById<Button>(R.id.signup)
        button2.setOnClickListener  {
            val intent = Intent(this, SignUp_pg::class.java)
            startActivity(intent)
            }
        val button3 = findViewById<Button>(R.id.button2)
        button3.setOnClickListener  {
            val intent = Intent(this, Home_pg::class.java)
            startActivity(intent)
        }
    }
}
