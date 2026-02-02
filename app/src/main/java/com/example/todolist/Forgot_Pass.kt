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
import com.google.firebase.firestore.firestore

class Forgot_Pass : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var EmailEditText: EditText
    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_pass)

        auth = Firebase.auth
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
//            auth = Firebase.auth

//            val currentUser = auth.currentUser
//        if (currentUser != null) {
//            db.collection("users")
//                .whereEqualTo("user_uid",currentUser.uid)

            val emailInput = EmailEditText.text.toString()

            if (emailInput.isEmpty()) {
                EmailEditText.error = "Please Enter your Email to reset password"
                EmailEditText.requestFocus()
                return@setOnClickListener
            }
            validateUserEmail(emailInput)
        }
    }

    private fun validateUserEmail(email: String) {
        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { document ->

                if (!document.isEmpty) {
                    sendResetEmail(email)
                } else {
                    Toast.makeText(this, "Email not fouhnd", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
    }

    private fun sendResetEmail(email: String) {


        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Check your Email", Toast.LENGTH_SHORT).show()


                    val intent = Intent(this, Otp_pg2::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(baseContext, "Invalid Email, Try Again", Toast.LENGTH_SHORT)
                        .show()


                }


                //add where condition to this and also private getname
            }


    }
}
