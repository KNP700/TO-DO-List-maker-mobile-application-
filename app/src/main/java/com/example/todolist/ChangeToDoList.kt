package com.example.todolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class ChangeToDoList : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private lateinit var topicEditText: EditText
    private lateinit var typeEditText: EditText
    private var currentNoteId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_todo_list)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        auth = Firebase.auth
        topicEditText = findViewById(R.id.topic)
        typeEditText = findViewById(R.id.type)


        currentNoteId = intent.getStringExtra("NOTE_ID")

//        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)

        if (currentNoteId != null) {
            loadNoteData(currentNoteId!!)
        }


        val buttonClose = findViewById<ImageView>(R.id.close)
        buttonClose.setOnClickListener {
            finish()
        }

        val buttonSave = findViewById<Button>(R.id.Save)
        buttonSave.setOnClickListener {
            finish()

            val updatedTitle = topicEditText.text.toString()
            val updatedContent = typeEditText.text.toString()

            if (updatedTitle.isEmpty()) {
                topicEditText.error = "Title cannot be empty"
                return@setOnClickListener



            }

            if (currentNoteId != null)
                updateNoteInfirestore(currentNoteId!!, updatedTitle, updatedContent)
        }
    }

    private fun loadNoteData(docId: String) {
        db.collection("todos").document(docId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val title = document.getString("title")
                    val description = document.getString("description")

                    topicEditText.setText(title)
                    typeEditText.setText(description)
                } else {
                    Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show()
                }

            }
            .addOnFailureListener { e -> Toast.makeText(this,"error", Toast.LENGTH_SHORT).show() }
    }


    private fun updateNoteInfirestore(docID: String, title: String, description: String) {
        val updates = mapOf(
            "title" to title,
            "description" to description
        )

        db.collection("todos").document(docID).update(updates)
            .addOnFailureListener {
                Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e->
                Toast.makeText(this,"fail", Toast.LENGTH_SHORT).show()
            }
    }
}
