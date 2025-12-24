package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUp_pg : AppCompatActivity() {


    private lateinit var usernameEditText: EditText
    private lateinit var createPasswordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerBtn: Button
    private lateinit var closeBtn: ImageView
    private lateinit var F_name: EditText
    private lateinit var L_name: EditText
    private lateinit var E_mail: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up_pg)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        usernameEditText = findViewById(R.id.Edit3)
        createPasswordEditText = findViewById(R.id.Edit5)
        confirmPasswordEditText = findViewById(R.id.Edit6)
        registerBtn = findViewById(R.id.button_register)
        closeBtn = findViewById(R.id.close)
        F_name = findViewById(R.id.Edit1)
        L_name = findViewById(R.id.Edit2)
        E_mail = findViewById(R.id.Edit4)


        closeBtn.setOnClickListener {
            
            finish()
        }


        registerBtn.setOnClickListener {


            val username = usernameEditText.text.toString()
            val password = createPasswordEditText.text.toString()
            val conPassword = confirmPasswordEditText.text.toString()
            val fName = F_name.text.toString()
            val lName = L_name.text.toString()
            val email = E_mail.text.toString()



            var isValid = true

            if (fName.isEmpty()) {
                F_name.error = "First name is required"
                isValid = false
                F_name.requestFocus()
            }

            if (lName.isEmpty()) {
                L_name.error = "Last name is required"
                isValid = false
                L_name.requestFocus()
            }

            if (username.isEmpty()) {
                usernameEditText.error = "Username is required"
                isValid = false
                usernameEditText.requestFocus()
//                return@setOnClickListener
            }

            if (password.isEmpty()) {
                createPasswordEditText.error = "Password cannot be empty"
                isValid = false
                createPasswordEditText.requestFocus()
//                return@setOnClickListener
            }

          if (email.isEmpty()) {
                E_mail.error = "Email cannot be empty"
                isValid = false
                E_mail.requestFocus()

            }

            if (password != conPassword) {
                Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show()
                confirmPasswordEditText.error = "password didint match, Try again"
                isValid = false
                confirmPasswordEditText.requestFocus()
    //                return@setOnClickListener
            }
            if (isValid) {
                Toast.makeText(this, "Success! Signing up...", Toast.LENGTH_SHORT).show()
                val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putString("user_name1", username)
                editor.apply()
                editor.putString("password", conPassword)
                editor.apply()
                editor.putString("fname", fName)
                editor.apply()
                editor.putString("lname", lName)
                editor.apply()
                editor.putString("email",email)
                editor.apply()
                editor.putString("pass", conPassword)
                editor.apply()



                val saveUserName2 = sharedPreferences.getString("topic", "NA")





//
//                Log.d("SignUp_pg", "Saved User: $username")


                val intent = Intent(this, Otp_pg2::class.java)
                startActivity(intent)
            }
        }
    }
}