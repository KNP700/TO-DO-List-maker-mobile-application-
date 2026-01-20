package com.example.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import com.example.todolist.Home_pg
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class FirebaseService : Service() {
    var db = Firebase.firestore
    var auth = Firebase.auth

    override fun onBind(intent: Intent): IBinder {
        print("onBind start")

        print("onBind end")
        TODO("Return the communication channel to the service.")
    }

    fun createCollection() {
        try {
            val testRef = db.collection("test")
            val data = hashMapOf(
                "test2" to "testdata"
            )
            testRef.add(data)
        } catch (e: Exception) {
            print(e)
        }

    }

    fun addTodo(title: String, description: String) {
         val auth = Firebase.auth
        val user = auth.currentUser

        if (user != null) {

            val todoRef = db.collection("todos")

            val data = hashMapOf(
                "title" to title,
                "description" to description,
                "user_uid" to user.uid
            )


            todoRef.add(data)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()


                    val intent = Intent(this, Home_pg::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
//                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
                }
        }
    }
}