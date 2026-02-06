//package com.example.todolist
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.google.firebase.Firebase
//import com.google.firebase.auth.EmailAuthProvider
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.auth
//
//class new_password2 : AppCompatActivity() {
//
//    private lateinit var newpassword: EditText
//    private lateinit var confpassword: EditText
//    private lateinit var curpassword: EditText
//    private lateinit var updateButton: Button
//    private lateinit var auth: FirebaseAuth
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_new_password2)
//
//        auth = Firebase.auth
//
//        // Initializing Views
//        newpassword = findViewById(R.id.new_password_input)
//        confpassword = findViewById(R.id.confirm_password_input)
//        curpassword = findViewById(R.id.old_password_input)
//        updateButton = findViewById(R.id.update)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        // Close button
//        findViewById<ImageView>(R.id.close2).setOnClickListener {
//            finish()
//        }
//
//        // Navigation to Forgot Password
//        findViewById<TextView>(R.id.Forgot).setOnClickListener {
//            startActivity(Intent(this, Forgot_Pass::class.java))
//            finish()
//        }
//
//        updateButton.setOnClickListener {
//            performPasswordUpdate()
//        }
//    }
//
//    private fun performPasswordUpdate() {
//        val curPass = curpassword.text.toString().trim()
//        val newPass = newpassword.text.toString().trim()
//        val confPass = confpassword.text.toString().trim()
//
//        // 1. Basic Validation
//        if (curPass.isEmpty() || newPass.isEmpty() || confPass.isEmpty()) {
//            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        if (newPass != confPass) {
//            confpassword.error = "Passwords do not match"
//            return
//        }
//
//        if (newPass.length < 6) {
//            newpassword.error = "Password must be at least 6 characters"
//            return
//        }
//
//        val user = auth.currentUser
//        val email = user?.email
//
//        if (user != null && email != null) {
//            // 2. Re-authenticate the user first (Required for sensitive operations like password change)
//            val credential = EmailAuthProvider.getCredential(email, curPass)
//
//            user.reauthenticate(credential).addOnCompleteListener { reauthTask ->
//                if (reauthTask.isSuccessful) {
//                    // 3. If re-auth is successful, update the password
//                    user.updatePassword(newPass).addOnCompleteListener { updateTask ->
//                        if (updateTask.isSuccessful) {
//                            Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show()
//                            // Go back to Login or Home
//                            startActivity(Intent(this, MainActivity::class.java))
//                            finish()
//                        } else {
//                            Toast.makeText(this, "Failed to update password: ${updateTask.exception?.message}", Toast.LENGTH_LONG).show()
//                        }
//                    }
//                } else {
//                    curpassword.error = "Incorrect current password"
//                    Toast.makeText(this, "Authentication failed. Check your current password.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//}