package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

class MainActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//       enableEdgeToEdge()

        setOnApplyWindowInsetsListener(findViewById(R.id.view_loging)) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            v.updatePadding(
                left = bars.left,
                top = bars.top,
                right = bars.right,
                bottom = bars.bottom,
            )
            WindowInsetsCompat.CONSUMED
        }

        usernameEditText = findViewById(R.id.Username_box)
        passwordEditText = findViewById(R.id.password_box)


        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val savedName = sharedPreferences.getString("user_name1", "")
        val passwordConfirm = sharedPreferences.getString("password", "")


//        println("Test >>>>>>>>>>>>>>>>>>>>>>")
// usernameEditText.setText(savedName)
        //passwordEditText.setText(passwordConfirm)

        Log.d("MainActivity", "Username : $savedName, $passwordConfirm")


        val forgotPassButton = findViewById<Button>(R.id.button)
        forgotPassButton.setOnClickListener {
            val intent = Intent(this, Forgot_Pass::class.java)
            startActivity(intent)
        }

        val signUpButton = findViewById<Button>(R.id.signup)
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUp_pg::class.java)
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.button2)
        loginButton.setOnClickListener {

            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()


            if (username == savedName && password == passwordConfirm) {
                println("Test >>>>>>>>>>>>>>>>>>>>>> $passwordConfirm")

                val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putBoolean("isLoggedIn", true) //check this
                editor.apply()

                val intent = Intent(this, Home_pg::class.java)
                startActivity(intent)
                finish()
            } else {
                Log.d("MainActivity", "Login Failed")
                android.widget.Toast.makeText(
                    this,
                    "Incorrect Username or Password. Try again",
                    android.widget.Toast.LENGTH_SHORT
                ).show()


            }
        }


//        if (usernameEditText == savedName && passwordEditText == passwordConfirm) {
//
//
//            Log.d("MainActivity", "Login Successful")
//            val intent = Intent(this, Home_pg::class.java)
//            startActivity(intent)
//            finish()
//
//        } else {
//
//
//            Log.d("MainActivity", "Login Failed")
//
//            android.widget.Toast.makeText(this, "Incorrect Username or Password", android.widget.Toast.LENGTH_SHORT).show()
//        }


//        println("Test >>>>>>>>>>>>>>>>>>>>>>")
        Log.d("MainActivity", "onCreate() called")
    }
}
