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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


private lateinit var auth: FirebaseAuth
private val db = Firebase.firestore

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
        auth = Firebase.auth
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


            val username = usernameEditText.text.toString().trim()
            val password = createPasswordEditText.text.toString().trim()
            val conPassword = confirmPasswordEditText.text.toString().trim()
            val fName = F_name.text.toString().trim()
            val lName = L_name.text.toString().trim()
            val email = E_mail.text.toString().trim()


            var isValid = true

            if (fName.isEmpty()) {
                F_name.error = "First name is required"
                if (isValid) F_name.requestFocus()
                isValid = false

            }

            if (lName.isEmpty()) {
                L_name.error = "Last name is required"
                if (isValid) L_name.requestFocus()
                isValid = false
            }

            if (username.isEmpty()) {
                usernameEditText.error = "Username is required"
                if (isValid) usernameEditText.requestFocus()
                isValid = false
            }

            if (password.isEmpty()) {
                createPasswordEditText.error = "Password is required"
                if (isValid) createPasswordEditText.requestFocus()
                isValid = false
            } else if (password.length < 6) {
                createPasswordEditText.error = "Password must be at least 6 characters"
                if (isValid) createPasswordEditText.requestFocus()
                isValid = false
            }

            if (conPassword.isEmpty()) {
                confirmPasswordEditText.error = "Please confirm your password"
                if (isValid) confirmPasswordEditText.requestFocus()
                isValid = false
            } else if (password != conPassword) {
                confirmPasswordEditText.error = "Passwords do not match"
                if (isValid) confirmPasswordEditText.requestFocus()
                isValid = false
            }

            if (email.isEmpty()) {
                E_mail.error = "Email cannot be empty"
                if (isValid) E_mail.requestFocus()
                isValid = false

            }

            if (isValid) {
                Toast.makeText(this, "Success! Signing up...", Toast.LENGTH_SHORT).show()


                createFirebaseUser(email, password, fName, lName, username)

//            if (password != conPassword) {
//                Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show()
//                confirmPasswordEditText.error = "password didint match, Try again"
//                isValid = false
//                confirmPasswordEditText.requestFocus()
//    //                return@setOnClickListener
//            }
            }
        }
    }

    private fun createFirebaseUser(
        email: String,
        pass: String,
        fname: String,
        lname: String,
        username: String
    ) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val userId = auth.currentUser?.uid
                    val userMap = hashMapOf(
                        "firstName" to fname,
                        "lastName" to lname,
                        "username" to username,
                        "email" to email

                    )

                    if (userId != null) {
                        db.collection("users").document(userId).set(userMap)
                            .addOnSuccessListener {

                                startActivity(Intent(this, Otp_pg2::class.java))
                                finish()
                            }
                    }
                } else {
                    Toast.makeText(this, "Error:", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }
}

//            if (isValid) {
//                Toast.makeText(this, "Success! Signing up...", Toast.LENGTH_SHORT).show()
//                val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
//                val editor = sharedPreferences.edit()
//
//                editor.putString("user_name1", username)
//                editor.apply()
//                editor.putString("password", conPassword)
//                editor.apply()
//                editor.putString("fname", fName)
//                editor.apply()
//                editor.putString("lname", lName)
//                editor.apply()
//                editor.putString("email",email)
//                editor.apply()
//                editor.putString("pass", conPassword)
//                editor.apply()


//                val saveUserName2 = sharedPreferences.getString("topic", "NA")


//
//                        Log.d("SignUp_pg", "Saved User: $username $conPassword")

//
//                val intent = Intent(this, Otp_pg2::class.java)
//                startActivity(intent)
//            }

