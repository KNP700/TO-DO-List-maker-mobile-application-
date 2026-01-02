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


            val saveBtn = findViewById<Button>(R.id.Save)
            val closeBtn = findViewById<ImageView>(R.id.close)


            closeBtn.setOnClickListener { finish() }


            saveBtn.setOnClickListener {

                val topicInput = findViewById<EditText>(R.id.topic)
                val contentInput = findViewById<EditText>(R.id.todo_content)
//

                val topic = topicInput.text.toString()
                val content = contentInput.text.toString()

                if (topic.isNotEmpty()) {
                    val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
                    val topicInput = sharedPreferences.getString("task_topic","")
                    val editor = sharedPreferences.edit()



                    val oldList = sharedPreferences.getString("task_list", "")

                    val newList = if (oldList.isNullOrEmpty()) {
                        topic
                    } else {
                        "$oldList,$topic"
                    }

                    editor.putString("task_list", newList)
                    editor.apply()
                    editor.putString("task_topic",topic)
                    editor.apply()
                    editor.putString("content_Input", content)
                    editor.apply()

                    Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    topicInput.error = "Topic is required"
                }
            }
        }

    }
}

