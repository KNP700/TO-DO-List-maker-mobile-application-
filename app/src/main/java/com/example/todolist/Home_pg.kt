package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Home_pg : AppCompatActivity() {

//    @SuppressLint("MissingInflatedId")

    private lateinit var topicEditText: Button

    //    private lateinit var typeEditText: EditText
    private lateinit var userName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_pg)
//        enableEdgeToEdge()

        topicEditText = findViewById(R.id.Demo)
        userName = findViewById(R.id.user3)


        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
// sharedPreferences.getStringSet()
        val saveTopic = sharedPreferences.getString("topic", "Test")
        val saveType = sharedPreferences.getString("type", "")
        val saveUserName = sharedPreferences.getString("user", "")
        val saveUserName1 = sharedPreferences.getString("user_name1", "")
//              topicEditText.setText(saveUserName1)
//      typeTextEdit.setText(saveUserName1)

        Log.d("Home_pg", "Topic : $saveUserName1")


        val button = findViewById<ImageView>(R.id.user)   // there is a issue, check this tomorrow
        button.setOnClickListener {
            val intent = Intent(this, User_detail::class.java)
            startActivity(intent)
        }
        val button2 = findViewById<TextView>(R.id.user2)
        button2.setOnClickListener {
            val intent = Intent(this, User_detail::class.java)
            startActivity(intent)
        }




        val textView = findViewById<TextView>(R.id.user3)
        textView.text = saveUserName1
//        textView.setOnClickListener {
//            val intent = Intent(this, SignUp_pg::class.java)
//            startActivity(intent)
//        }

        val button3 = findViewById<ImageView>(R.id.menu)
        button3.setOnClickListener {
            val intent = Intent(this, Menu_pg::class.java)
            startActivity(intent)
        }

        val button4 = findViewById<Button>(R.id.Add_list)
        button4.setOnClickListener {
            val intent = Intent(this, Add_list::class.java)
            startActivity(intent)
        }

        val button5 = findViewById<Button>(R.id.Demo)
        button5.text = saveTopic
        button5.setOnClickListener {
            val intent = Intent(this, Demo_pg::class.java)
            startActivity(intent)
        }

        Log.d("Home.pg", "Saved User: $saveUserName1")


//        println("Test >>>>>>>>>>>>>>>>>>>>>>$saveTopic")
        println("Test >>>>>>>>>>>>>>>>>>>11>>>>>$saveUserName1")
        Log.d("Home_pg", "onCreate() called")

    }
}
