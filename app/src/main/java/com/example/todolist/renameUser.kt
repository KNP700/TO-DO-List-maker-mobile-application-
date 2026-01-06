//package com.example.todolist
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//
//class renameUser : AppCompatActivity() {
//
//
//    private lateinit var username1: EditText
//    private lateinit var confUser: EditText
//
////    private lateinit var preUser : EditText
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_rename_user)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//        val button2 = findViewById<ImageView>(R.id.close2)
//        button2.setOnClickListener {
//            finish()
//        }
//
//
//        val button1 = findViewById<TextView>(R.id.update)
//        button1.setOnClickListener {
//
//            val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
//            val savedName = sharedPreferences.getString("user_name1", "")
//
//
//            username1 = findViewById(R.id.new_Username_input)
//            confUser = findViewById(R.id.confirm_Username_input)
////        preUser = findViewById(R.id.Edit3)
//
//
//            val username = username1.text.toString()
//            val confUser1 = confUser.text.toString()
////            val preUser1 = preUser.text.toString()
//
//            var isValid = true
//
//            if (username.isEmpty()) {
//                username1.error = "Current Username is required"
//                isValid = false
//                username1.requestFocus()
//            }
//
//            if (username != savedName) {
//                Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
//                username1.error = "Username Didnt match, Try again"
//                isValid = false
//                username1.requestFocus()
////
//            }
//            if (confUser1.isEmpty()) {
//                confUser.error = "Cannot empty this field"
//                isValid = false
//                confUser.requestFocus()
//
//            }
//
//            if (isValid) {
//                Toast.makeText(this, "Username change success!", Toast.LENGTH_SHORT).show()
//                val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
////                val currentUser = sharedPreferences.getString("user_name1","")
//                val editor = sharedPreferences.edit()
//
//                editor.putString("user_name1", confUser1)
//                editor.apply()
//
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            }
//
//
//        }
//
//    }
//}
