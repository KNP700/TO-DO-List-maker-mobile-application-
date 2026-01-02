package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Demo_pg : AppCompatActivity() {

    private lateinit var topicEditText: EditText
    private lateinit var typeEditText: EditText
    private var currentNoteId: String? = null // Store the ID here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_pg)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        topicEditText = findViewById(R.id.topic)
        typeEditText = findViewById(R.id.type)

        // 1. GET THE ID PASSED FROM HOME
        currentNoteId = intent.getStringExtra("NOTE_ID")

        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)

        if (currentNoteId != null) {
            // 2. LOAD DATA USING ID
            val savedTitle = sharedPreferences.getString("title_$currentNoteId", "")
            val savedContent = sharedPreferences.getString("content_$currentNoteId", "")

            topicEditText.setText(savedTitle)
            typeEditText.setText(savedContent)
        }

        val buttonClose = findViewById<ImageView>(R.id.close)
        buttonClose.setOnClickListener {
            finish()
        }

        val buttonSave = findViewById<Button>(R.id.Save)
        buttonSave.setOnClickListener {

            val updatedTitle = topicEditText.text.toString()
            val updatedContent = typeEditText.text.toString()

            if (currentNoteId != null) {
                val editor = sharedPreferences.edit()

                // 3. UPDATE DATA FOR THIS ID
                editor.putString("title_$currentNoteId", updatedTitle)
                editor.putString("content_$currentNoteId", updatedContent)
                editor.apply()

                Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show()

                // Return to Home
                val intent = Intent(this, Home_pg::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }
    }
}