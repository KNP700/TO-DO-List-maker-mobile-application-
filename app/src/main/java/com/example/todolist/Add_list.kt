package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.content.edit

class Add_list : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
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
                    sharedPreferences.edit {

                        val oldList = sharedPreferences.getString("task_list", "")

                        val newList = if (oldList.isNullOrEmpty()) {
                            topic
                        } else {
                            "$oldList,$topic"
                        }

                        putString("task_list", newList)
                        putString("content_$topic", content)
                    }

                    Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    topicInput.error = "Topic is required"
                }
            }
        }

    }
}

