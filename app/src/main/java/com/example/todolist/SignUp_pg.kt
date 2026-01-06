package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class SignUp_pg : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var createPasswordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerBtn: Button
    private lateinit var closeBtn: ImageView
    private lateinit var F_name: EditText
    private lateinit var L_name: EditText
    private lateinit var E_mail: EditText

    // 1. Declare Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_pg)

        // 2. Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

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
                isValid = false
                F_name.requestFocus()
            } else if (lName.isEmpty()) {
                L_name.error = "Last name is required"
                isValid = false
                L_name.requestFocus()
            } else if (username.isEmpty()) {
                usernameEditText.error = "Username is required"
                isValid = false
                usernameEditText.requestFocus()
            } else if (email.isEmpty()) {
                E_mail.error = "Email cannot be empty"
                isValid = false
                E_mail.requestFocus()
            } else if (password.isEmpty()) {
                createPasswordEditText.error = "Password cannot be empty"
                isValid = false
                createPasswordEditText.requestFocus()
            } else if (password != conPassword) {
                confirmPasswordEditText.error = "Passwords do not match"
                isValid = false
                confirmPasswordEditText.requestFocus()
            }

            if (isValid) {
                // 3. Create User in Firebase
                registerBtn.isEnabled = false // Disable button to prevent double clicks
                Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show()

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d("SignUp_pg", "createUserWithEmail:success")

                            // Optional: Save the User's Name to their Profile
                            val user = firebaseAuth.currentUser
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName("$fName $lName")
                                .build()

                            user?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener {
                                    // Profile updated
                                }

                            // 4. Navigate to next screen (OTP or Home)
                            // Note: Firebase handles user creation securely, so you might not strictly need Otp_pg2
                            // unless you have specific logic there.
                            val intent = Intent(this, Otp_pg2::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            // If sign in fails, display a message to the user.
                            registerBtn.isEnabled = true
                            Log.w("SignUp_pg", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Registration failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}