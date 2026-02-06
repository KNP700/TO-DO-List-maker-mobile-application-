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
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth
//import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

//private val new_password2.auth:

class NewPassword2 : AppCompatActivity() {


    private lateinit var newPassword: EditText
    private lateinit var confPassword: EditText
    private lateinit var updatepassword: Button
    private lateinit var curpassword: EditText
    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_new_password2)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        val currentPassword = "";
//        val newPassword = "";

        auth = Firebase.auth


        newPassword = findViewById(R.id.new_password_input)
        confPassword = findViewById(R.id.confirm_password_input)
        updatepassword = findViewById(R.id.update)
        curpassword = findViewById(R.id.old_password_input)

        findViewById<ImageView>(R.id.close2).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.Forgot).setOnClickListener {
            startActivity(Intent(this, Forgot_Pass::class.java))
            finish()
        }

        updatepassword.setOnClickListener {
            passwordUpdate()


        }
    }

    private fun passwordUpdate() {

//        try {
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
            return

        }

        if (confpass.isEmpty()) {
            confPassword.error = "cannot empty this fields"
            isValid = false
            confPassword.requestFocus()
            return


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
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }

                    }
                }
            }

        } else {
            curpassword.error = "Incorrect current password"
            Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show()
        }
//
//        startActivity(Intent(this, MainActivity::class.java))
//        finish()
//        } catch (e: FirebaseAuthException) {
//            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
//        }
    }
}
//
//            }
//
//            if (curPass != saveDPass) {
//                curpassword.error = "current password didnt match, Try again"
//                isValid = false
//                curpassword.requestFocus()
//            }
//
//
//
//
//
//
//            if (newPass != confpass) {
//                Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show()
//                confpassword.error = "Password didnt match. Try again"
//                isValid = false
//                confpassword.requestFocus()
//
//            }
//            if (isValid) {
//                Toast.makeText(this, "Success! Updated your password...", Toast.LENGTH_SHORT)
//                    .show()
//                val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
//                val editor = sharedPreferences.edit()
//
//                editor.putString("password", newPass)
//                editor.apply()
//                editor.putString("pass", confpass)
//
//
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            }
//
//        }
//    }
//}
