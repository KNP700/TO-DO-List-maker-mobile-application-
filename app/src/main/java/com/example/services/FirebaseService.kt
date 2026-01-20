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
import kotlinx.coroutines.tasks.await

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

    suspend fun addTodo(title: String, description: String): Boolean {
        try {
            val auth = Firebase.auth
            val user = auth.currentUser

            if (user != null) {

                val todoRef = db.collection("todos")

                val data = hashMapOf(
                    "title" to title,
                    "description" to description,
                    "user_uid" to user.uid
                )
                var x: Boolean = false
                todoRef.add(data)
                    .addOnSuccessListener { documentReference ->
//                        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                        x = true
                    }
                    .addOnFailureListener { e ->
//                        Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
                    }.await()
                return  x
            }
            return false
        } catch (e: Exception) {
            print(e)
            return false
        }
    }
}