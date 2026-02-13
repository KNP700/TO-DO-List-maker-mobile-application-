package com.example.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.todolist.HomePage
import com.example.todolist.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class AuthenticationService : Service() {
    var currentUser: FirebaseUser?
        get() {
            TODO()
        }
        set(value) {}
    var auth: FirebaseAuth = Firebase.auth

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    suspend fun login(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun logout() {}

    fun getUserData() {}
}