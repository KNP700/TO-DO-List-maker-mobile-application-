package com.example.todolist

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Demo_pg : AppCompatActivity() {

    private lateinit var topicEditText: EditText
    private lateinit var typeEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_demo_pg)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        topicEditText = findViewById(R.id.topic)
        typeEditText = findViewById(R.id.type)






        val button = findViewById<ImageView>(R.id.close)
        button.setOnClickListener {
           finish()
        }
        val button2 = findViewById<Button>(R.id.Save)
        println("button2:$button2")
//        Log.d("onCreate: button2 : $button2")
        button2.setOnClickListener {

            val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE )
            val editor = sharedPreferences.edit()


            editor.putString("topic", topicEditText.text.toString())
            editor.apply()
            editor.putString("type", typeEditText.text.toString())
            editor.apply()


            val saveTopic = sharedPreferences.getString("topic", "NA")


            val saveType = sharedPreferences.getString("type","NA")






            println("Test >>>>>>>>>>>>>>>>>>>>>> $saveTopic")
            print("Test >>>>>>>>>>>>>>>>>>>>> $saveType")


            Log.d("Home_pg", "onCreate() called")





            val intent = Intent(this, Home_pg::class.java)
            startActivity(intent)
        }
    }
}