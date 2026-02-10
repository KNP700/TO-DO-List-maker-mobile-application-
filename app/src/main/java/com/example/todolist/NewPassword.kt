package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
//import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class NewPassword : AppCompatActivity() {


    private lateinit var newPassword: EditText
    private lateinit var confPassword: EditText
    private lateinit var updatepassword: Button
    private lateinit var curpassword: EditText
    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_new_password)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth


        newPassword = findViewById(R.id.new_password_input)
        confPassword = findViewById(R.id.confirm_password_input)
        updatepassword = findViewById(R.id.update)
        curpassword = findViewById(R.id.old_password_input)

        findViewById<ImageView>(R.id.close2).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.Forgot).setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
            finish()
        }

        updatepassword.setOnClickListener {
            passwordUpdate()


        }
    }

    private fun passwordUpdate() {


        val newPass = newPassword.text.toString()
        val confpass = confPassword.text.toString()
        val curPass = curpassword.text.toString()


        var isValid = true

        if (curPass.isEmpty()) {
            curpassword.error = "cannot empty this fields"
            isValid = false
            curpassword.requestFocus()
            return


        }
//            auth.signInWithEmail

        if (newPass.isEmpty()) {
            newPassword.error = "cannot empty this fields"
            isValid = false
            newPassword.requestFocus()
        } else if (newPass.length < 8) {
            newPassword.error = "Password must be at least 8 characters"
            if (isValid) newPassword.requestFocus()
            isValid = false
        } else if (!newPass.any { it.isDigit() }) {
            newPassword.error = "Password must contain at least 1 numeric character"
            if (isValid) newPassword.requestFocus()
            isValid = false
        } else if (!newPass.any { it.isUpperCase() }) {
            newPassword.error = "Password must contain at least Uppercase letter"
            if (isValid) newPassword.requestFocus()
            isValid = false
        }



        if (confpass.isEmpty()) {
            confPassword.error = "cannot empty this fields"
            isValid = false
            confPassword.requestFocus()
        } else if (newPass != confpass) {
            confPassword.error = "Password do not match"
            if (isValid) confPassword.requestFocus()
            isValid = false
        }
        val user = auth.currentUser
        val email = user?.email


        if (user != null && email != null) {
            val credential = EmailAuthProvider.getCredential(email, curPass)

            user.reauthenticate(credential).addOnCompleteListener { reauth ->
                if (reauth.isSuccessful) {
                    user.updatePassword(newPass).addOnCompleteListener { e ->
                        if (e.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Password update successfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            startActivity(Intent(this, SignInPage::class.java))
                            finish()
                        }

                    }
                }
            }

        } else {
            curpassword.error = "Incorrect current password"
            Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show()
        }
    }
}