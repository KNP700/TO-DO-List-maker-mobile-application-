package com.example.todolist

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.services.FirebaseService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch


class AddList : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    val firebaseService = FirebaseService()

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

//            val service = FirebaseService()

            if (topic.isNotEmpty()) {
                lifecycleScope.launch { createFirebaseList(topic, content) }
            } else {
                topicInput.error = "Topic is required"
            }
        }
    }

    private suspend fun createFirebaseList(
        title: String,
        description: String,

        ) {

        val progress = findViewById<ProgressBar>(R.id.progress)
        progress.visibility = View.VISIBLE

        val job = lifecycleScope.launch {
            val res = firebaseService.addTodo(
                title, description
            )
            if (res) {
                finish()
            }
        }

        // wait for the job to complete
        job.join()

        progress.visibility = View.GONE
    }
}
