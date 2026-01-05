package com.example.todolist

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class Home_pg : AppCompatActivity() {

    private var backPressedOnce = false
    private lateinit var userName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_pg)

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

    override fun onConfigurationChanged(newConfig: Configuration) {
        refreshButtons()
        super.onConfigurationChanged(newConfig)
    }

    private fun refreshButtons() {
        val container = findViewById<GridLayout>(R.id.buttonContainer)
        container.removeAllViews()

        val orientation = resources.configuration.orientation
        container.columnCount = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 2 else 1

        userName = findViewById(R.id.user3)
        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)

        val saveUserName1 = sharedPreferences.getString("user_name1", "")
        userName.text = saveUserName1

        val idListString = sharedPreferences.getString("task_id_list", "")

        val userBtn = findViewById<ImageView>(R.id.user)
        userBtn.setOnClickListener { startActivity(Intent(this, User_detail::class.java)) }

        val userTxt = findViewById<TextView>(R.id.user2)
        userTxt.setOnClickListener { startActivity(Intent(this, User_detail::class.java)) }

        val menuBtn = findViewById<ImageView>(R.id.menu)
        menuBtn.setOnClickListener { startActivity(Intent(this, Menu_pg::class.java)) }


        if (!idListString.isNullOrEmpty()) {
            val idArray = idListString.split(",")
            for (idStr in idArray) {
                if (idStr.isNotEmpty()) {
                    createButton(idStr, container)
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun createButton(idStr: String, container: GridLayout) {
        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)


        val topicName = sharedPreferences.getString("title_$idStr", "No Title") ?: "No Title"

        val gridParams = GridLayout.LayoutParams().apply {
            height = 200
            width = 0
            columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            setMargins(40, 30, 40, 0)
        }

        val stack = FrameLayout(this).apply {
            layoutParams = gridParams
        }

        val newBtn = Button(this).apply {
            text = topicName
            isAllCaps = false
            textSize = 30f
            setTextColor(getColor(R.color.black))
            setBackgroundResource(R.drawable.todo_bg)
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )

            setOnClickListener {

                val intent = Intent(context, Demo_pg::class.java)
                intent.putExtra("NOTE_ID", idStr)
                startActivity(intent)
            }
        }

        val deleteBtn = Button(this).apply {
            text = "X"
            textSize = 23f
            setBackgroundColor(android.R.color.transparent)
            layoutParams = FrameLayout.LayoutParams(130, 60).apply {
                gravity = android.view.Gravity.TOP or android.view.Gravity.END
                setMargins(0, 20, 0, 0)
            }
            setOnClickListener {
                deleteTopic(idStr) // Delete by ID
            }
        }

        stack.addView(newBtn)
        stack.addView(deleteBtn)
        container.addView(stack)
    }

    private fun deleteTopic(idToDelete: String) {
        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val idListString = sharedPreferences.getString("task_id_list", "")

        if (!idListString.isNullOrEmpty()) {
            val idList = idListString.split(",").toMutableList()

            idList.remove(idToDelete)
            val newIdListString = idList.joinToString(",")

            val editor = sharedPreferences.edit()
            editor.putString("task_id_list", newIdListString)


            editor.remove("title_$idToDelete")
            editor.remove("content_$idToDelete")

            editor.apply()

            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
            refreshButtons()
        }
    }
}