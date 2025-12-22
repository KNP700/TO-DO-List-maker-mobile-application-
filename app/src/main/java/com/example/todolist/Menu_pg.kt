package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class Menu_pg : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_pg)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button = findViewById<ImageView>(R.id.close1)
        button.setOnClickListener {
            val intent = Intent(this, Home_pg::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<TextView>(R.id.Logout)
        button2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val button3 = findViewById<TextView>(R.id.Settings1)
        button3.setOnClickListener {
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }

        val categoriesHeader = findViewById<TextView>(R.id.Categories)
        val expandableLayout = findViewById<LinearLayout>(R.id.expandableContentLayout)
        val cardView = findViewById<CardView>(R.id.myCardView)

        categoriesHeader.setOnClickListener {

            val isVisible = expandableLayout.visibility == View.VISIBLE

            TransitionManager.beginDelayedTransition(cardView, AutoTransition())


            if (isVisible) {
                expandableLayout.visibility = View.GONE
            } else {
                expandableLayout.visibility = View.VISIBLE
            }
        }





    }
}