package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class new_password2 : AppCompatActivity() {


    private lateinit var newpassword: EditText
    private lateinit var confpassword: EditText
    private lateinit var updatepassword: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_password2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        newpassword = findViewById(R.id.new_password_input)
        confpassword = findViewById(R.id.confirm_password_input)
        updatepassword = findViewById(R.id.update)

        val button = findViewById<ImageView>(R.id.close2)
        button.setOnClickListener {
            finish()
        }


        updatepassword.setOnClickListener {

            val newPass = newpassword.text.toString()
            val confpass = confpassword.text.toString()


            var isValid = true


            if (newPass.isEmpty()) {
                newpassword.error = "cannot empty this fields"
                isValid = false
                newpassword.requestFocus()

            }

            if (confpass.isEmpty()) {
                confpassword.error = "cannot empty this fields"
                isValid = false
                confpassword.requestFocus()


            }
            if (newPass != confpass) {
                Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show()
                confpassword.error = "Password  macth. Try again"
                isValid = false
                confpassword.requestFocus()

            }
            if (isValid) {
                Toast.makeText(this, "Success! Updated your password...", Toast.LENGTH_SHORT)
                    .show()
                val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putString("password", newPass)
                editor.apply()
                editor.putString("pass", confpass)


                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }
    }
}
