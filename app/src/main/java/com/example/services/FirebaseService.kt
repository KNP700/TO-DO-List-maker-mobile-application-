package com.example.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class FirebaseService : Service() {
    lateinit var db: FirebaseFirestore
    override fun onBind(intent: Intent): IBinder {
        print("onBind start")

        print("onBind end")
        TODO("Return the communication channel to the service.")
    }
    fun createCollection(){
        db = Firebase.firestore
        try {
            val testRef = db.collection("test")
            val data = hashMapOf(
                "test2" to "testdata"
            )
            testRef.add(data)
        }catch (e: Exception){
            print(e)
        }

    }
    fun addTodo(title:String,description:String){
        db = Firebase.firestore
        try {
            val todoRef = db.collection("todos")
            val data = hashMapOf(
                "title" to title,
                "description" to description
            )
            todoRef.add(data)
        }catch (e: Exception){
            print(e)
        }
    }

//    getTodos

}