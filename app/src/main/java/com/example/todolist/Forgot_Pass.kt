package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Forgot_Pass : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var EmailEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_pass)

        auth = Firebase.auth

        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            startActivity(Intent(this, otp_pg::class.java))
//            finish()
//
//        }

        EmailEditText = findViewById(R.id.textView4)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button = findViewById<ImageView>(R.id.close)
        button.setOnClickListener {
            finish()
        }

        val button2 = findViewById<Button>(R.id.reset)
        button2.setOnClickListener {

            val emailInput = EmailEditText.text.toString()

            if (emailInput.isEmpty()) {
                EmailEditText.error = "Please Enter your Email to reset password"
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(emailInput)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "Correct", Toast.LENGTH_SHORT).show()


                        val intent = Intent(this, otp_pg::class.java)
                        startActivity(intent)

                    }else{
                        Toast.makeText(baseContext,"Invalid Email, Try Again", Toast.LENGTH_SHORT).show()

                    }
        }


        }}
    }