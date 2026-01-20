package com.example.todolist

import android.icu.text.CaseMap
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.services.FirebaseService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


class Add_list : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
//    private

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
                createFirebaseList(topic,content)
//                val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
//                val editor = sharedPreferences.edit()
//
//
//                val newId = sharedPreferences.getInt("next_id", 1)
//
//
//                editor.putString("title_$newId", topic)
//                editor.putString("content_$newId", content)
//
//
//                val oldIdList = sharedPreferences.getString("task_id_list", "")
//                val newIdList = if (oldIdList.isNullOrEmpty()) {
//                    "$newId"
//                } else {
//                    "$oldIdList,$newId"
//                }
//                editor.putString("task_id_list", newIdList)
//
//
//                editor.putInt("next_id", newId + 1)
//
//                editor.apply()
//
//                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
//                finish()
            } else {
                topicInput.error = "Topic is required"
            }
        }
    }

    private fun createFirebaseList(
        title: String,
        description: String,
    ) {
        val firebaseService = FirebaseService()
        firebaseService.addTodo(
            title, description
        )
//        auth.createTitleWithDescription(title, Description)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful)
//
//
//            }
    }
}
