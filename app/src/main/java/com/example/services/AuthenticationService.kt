package com.example.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

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

    fun login() {
        currentUser = auth.currentUser;
    }

    fun logout() {}

    fun getUserData() {}
}