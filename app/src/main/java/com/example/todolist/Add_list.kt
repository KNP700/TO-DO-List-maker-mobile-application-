package com.example.todolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Add_list : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_list)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val topicInput = findViewById<EditText>(R.id.topic)
        val contentInput = findViewById<EditText>(R.id.todo_content)
        val saveBtn = findViewById<Button>(R.id.Save)
        val closeBtn = findViewById<ImageView>(R.id.close)

        closeBtn.setOnClickListener { finish() }

        saveBtn.setOnClickListener {
            val topic = topicInput.text.toString()
            val content = contentInput.text.toString()

            if (topic.isNotEmpty()) {
                val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                // 1. GET THE NEXT AVAILABLE ID (Default to 1)
                val newId = sharedPreferences.getInt("next_id", 1)

                // 2. SAVE DATA USING THIS ID
                editor.putString("title_$newId", topic)
                editor.putString("content_$newId", content)

                // 3. ADD THIS ID TO THE LIST OF IDS
                // We store ids like "1,2,5" instead of names
                val oldIdList = sharedPreferences.getString("task_id_list", "")
                val newIdList = if (oldIdList.isNullOrEmpty()) {
                    "$newId"
                } else {
                    "$oldIdList,$newId"
                }
                editor.putString("task_id_list", newIdList)

                // 4. PREPARE THE ID FOR THE NEXT NOTE
                editor.putInt("next_id", newId + 1)

                editor.apply()

                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                topicInput.error = "Topic is required"
            }
        }
    }
}