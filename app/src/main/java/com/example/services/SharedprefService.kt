package com.example.services

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedprefService(context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPref.edit { putBoolean("isLoggedIn", isLoggedIn) }



    }

    fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean("isLoggedIn", false)
    }


}