package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MenuPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_page)


        auth = Firebase.auth

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val button = findViewById<ImageView>(R.id.close1)
        button.setOnClickListener {
            finish()
        }


//        val button2 = findViewById<TextView>(R.id.Logout)
//        button2.setOnClickListener {
//
//            showLogoutDialog()


//            auth.signOut()
//            val checkPrefs = getSharedPreferences("UserPreferences", MODE_PRIVATE)
//            val isLoggedIn = checkPrefs.getBoolean("isLoggedIn", false)
//            val editor = checkPrefs.edit()
//
//            editor.putBoolean("isLoggedIn", false)
//
//            val intent = Intent(this, SignInPage::class.java)
//
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//            editor.apply()
//
//            startActivity(intent)
//            finish()


        val button3 = findViewById<TextView>(R.id.Settings1)
        button3.setOnClickListener {
            val intent = Intent(this, SettingPage::class.java)
            startActivity(intent)
        }


        val button2 = findViewById<TextView>(R.id.Logout)
        button2.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout?")

        builder.setPositiveButton("yes") { dialog, which ->

            showLogoutDialog()
            auth.signOut()
            val checkPrefs = getSharedPreferences("UserPreferences", MODE_PRIVATE)
            val isLoggedIn = checkPrefs.getBoolean("isLoggedIn", false)
            val editor = checkPrefs.edit()

            editor.putBoolean("isLoggedIn", false)

            val intent = Intent(this, SignInPage::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            editor.apply()

            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()


    }

}