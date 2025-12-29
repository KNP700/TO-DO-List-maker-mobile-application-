package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class Home_pg : AppCompatActivity() {

    //    @SuppressLint("MissingInflatedId")
    private var backPressedOnce = false

    private lateinit var topicEditText: Button

    //    private lateinit var typeEditText: EditText
    private lateinit var userName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_pg)
//        enableEdgeToEdge()


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedOnce) {
                    finishAffinity()
                    return
                }

                backPressedOnce = true
                Toast.makeText(this@Home_pg, "Press back again to exit", Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    backPressedOnce = false
                }, 2000)
            }
        })

        val addListBtn = findViewById<Button>(R.id.Add_list)
        addListBtn.setOnClickListener {
            val intent = Intent(this, Add_list::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshButtons()
    }

    private fun refreshButtons() {

        val container = findViewById<LinearLayout>(R.id.buttonContainer)

        container.removeAllViews()




        topicEditText = findViewById(R.id.Demo)
        userName = findViewById(R.id.user3)


        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val taskString = sharedPreferences.getString("task_list", "")
//        val saveTopic = sharedPreferences.getString("topic", "Test")
//        val saveType = sharedPreferences.getString("type", "")
//        val saveUserName = sharedPreferences.getString("user", "")
        val saveUserName1 = sharedPreferences.getString("user_name1", "")
//              topicEditText.setText(saveUserName1)
//      typeTextEdit.setText(saveUserName1)


        val button =
            findViewById<ImageView>(R.id.user)   // there is a issue, check this tomorrow
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

//        val button5 = findViewById<Button>(R.id.Demo)
//        button5.text = saveTopic
//        button5.setOnClickListener {
//            val intent = Intent(this, Demo_pg::class.java)
//            startActivity(intent)
//        }


        Log.d("Home.pg", "Saved User: $saveUserName1")


//        println("Test >>>>>>>>>>>>>>>>>>>>>>$saveTopic")
        println("Test >>>>>>>>>>>>>>>>>>>11>>>>>$saveUserName1")
        Log.d("Home_pg", "onCreate() called")

//


        if (!taskString.isNullOrEmpty()) {
            val taskList = taskString.split(",")

            for (topic in taskList) {
                createButton(topic, container)
            }
        }
    }


    private fun createButton(topicName: String, container: LinearLayout) {
        val rowLayout = LinearLayout(this)
        rowLayout.orientation = LinearLayout.HORIZONTAL
        val rowParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        rowParams.setMargins(4, 30, 40, 0)
        rowLayout.layoutParams = rowParams


        val newBtn = Button(this)
        newBtn.text = topicName
        newBtn.isAllCaps = false
        newBtn.textSize = 20f
        newBtn.setTextColor(getColor(R.color.black))

        newBtn.setBackgroundResource(R.drawable.todo_bg)

        val topicParams = LinearLayout.LayoutParams(
            0,
            350
        )

        topicParams.weight = 1f
        newBtn.layoutParams = topicParams

        newBtn.setOnClickListener {

            val intent = Intent(this, Demo_pg::class.java)

            intent.putExtra("TOPIC_KEY", topicName)

            startActivity(intent)


//            val prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE)
//            val content = prefs.getString("content_$topicName", "No content")
//            Toast.makeText(this, content, Toast.LENGTH_LONG).show()
        }

        val textPart = TextView(this)
        val deleteBtn = Button(this)
        deleteBtn.text = "Delete"
        deleteBtn.textSize = 18f


        val deleteParams = LinearLayout.LayoutParams(
            120,
            120
        )

        deleteParams.setMargins(15, 0, 0, 0)
        deleteParams.gravity = android.view.Gravity.CENTER_VERTICAL
        deleteBtn.layoutParams = deleteParams



        textPart.setOnClickListener { }
        deleteBtn.setOnClickListener {
            deleteTopic(topicName)
        }


        rowLayout.addView(newBtn)
        rowLayout.addView(deleteBtn)
        container.addView(rowLayout)
    }

    private fun deleteTopic (topicToDelete : String){
        val sharedPreferences = getSharedPreferences("UserPreferences",MODE_PRIVATE)
        val taskString = sharedPreferences.getString("task_list","")

        if (!taskString.isNullOrEmpty()){
            val taskList = taskString.split(",").toMutableList()

            taskList.remove(topicToDelete)

            val newListString = taskList.joinToString (",")

            val editor = sharedPreferences.edit()
            editor.putString("task_List", newListString)
            editor.remove("content_$topicToDelete")
            editor.apply()

            Toast.makeText(this,"Deleted $topicToDelete", Toast.LENGTH_SHORT).show()
            refreshButtons()
        }
    }


}









