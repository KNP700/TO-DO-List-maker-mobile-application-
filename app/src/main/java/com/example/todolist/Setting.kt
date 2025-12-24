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

class Setting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  enableEdgeToEdge()
        setContentView(R.layout.activity_setting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val button1 = findViewById<ImageView>(R.id.close)
        button1.setOnClickListener {
            finish()
        }

        val button3 = findViewById<TextView>(R.id.changeUser)
        button3.setOnClickListener {
            val intent = Intent(this, confUsername::class.java)
            startActivity(intent)


        }

        val button4 = findViewById<TextView>(R.id.chaPass)
        button4.setOnClickListener {
            val intent = Intent(this, changePass::class.java)
            startActivity(intent)
        }

        val categoriesHeader = findViewById<TextView>(R.id.account)
        val expandableLayout = findViewById<LinearLayout>(R.id.expandableContentLayout2)
        val cardView = findViewById<CardView>(R.id.myCardView2)

        categoriesHeader.setOnClickListener {

            val isVisible = expandableLayout.visibility == View.VISIBLE

            TransitionManager.beginDelayedTransition(cardView, AutoTransition())


            if (isVisible) {
                expandableLayout.visibility = View.GONE
            } else {
                expandableLayout.visibility = View.VISIBLE
            }
        }
        val button2 = findViewById<TextView>(R.id.accDe)
        button2.setOnClickListener {
            val intent = Intent(this, Update_detail_pg::class.java)
            startActivity(intent)
        }



    }
}