package com.example.todolist

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.GridView
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
    private lateinit var container: GridView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_pg)
        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
//        enableEdgeToEdge()
        container = findViewById(R.id.buttonContainer)

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
        val button =
            findViewById<ImageView>(R.id.user)

        val button2 = findViewById<TextView>(R.id.user2)
        button2.setOnClickListener {
            val intent = Intent(this, User_detail::class.java)
            startActivity(intent)
        }


        val textView = findViewById<TextView>(R.id.user3)
        val saveUserName1 = sharedPreferences.getString("user_name1", "")
        textView.text = saveUserName1
        textView.setOnClickListener {
            val intent = Intent(this, SignUp_pg::class.java)
            startActivity(intent)
        }

        val button3 = findViewById<ImageView>(R.id.menu)
        button3.setOnClickListener {
            val intent = Intent(this, Menu_pg::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshButtons()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        println("newConfig.orientation ${newConfig.orientation}")
        Log.d(TAG, "onConfigurationChanged: ${newConfig.orientation}")

//        refreshButtons()
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            container.columnCount = 2
            println("Landscape Mode")
        } else {
//            container.columnCount = 1
            println("Portrait Mode")
        }
//        container.requestLayout()
//        container.invalidate()
        super.onConfigurationChanged(newConfig)
        refreshButtons()
    }

    private fun refreshButtons() {

        val taskString = sharedPreferences.getString("task_list", "") ?: ""
        val saveUserName1 = sharedPreferences.getString("user_name1", "")

//        userName.text = saveUserName1

        // Convert comma-separated string to List
        val taskList = if (taskString.isNotEmpty()) {

            taskString.split(",").filter { it.isNotEmpty() }.toMutableList()

        } else {

            mutableListOf()

        }

        // Handle Orientation columns
        val orientation = resources.configuration.orientation

        container.numColumns =
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) 2 else 1// Initialize and set the Adapter
        val adapter = GridViewAdapter(
            this,
            taskList,
            onDeleteClick = { topic -> deleteTopic(topic) },
            onItemClick = { topic ->
                val intent = Intent(this, Demo_pg::class.java)
                intent.putExtra("TOPIC_KEY", topic)
                startActivity(intent)
            }

        )

        container.adapter = adapter

    }


//    private fun refreshButtons() {
//
//
//
//
////        container.removeAllViews()
//
//
//        val orientation = resources.configuration.orientation
//        Log.d(TAG, "refreshButtons: $orientation")
//        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            container.numColumns = 2
//        } else {
//            container.numColumns = 1
//        }
//        topicEditText = findViewById(R.id.Demo)
//        userName = findViewById(R.id.user3)
//
//
//        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
//        val taskString = sharedPreferences.getString("task_list", "")
////        val saveTopic = sharedPreferences.getString("topic", "Test")
////        val saveType = sharedPreferences.getString("type", "")
////        val saveUserName = sharedPreferences.getString("user", "")
//        val saveUserName1 = sharedPreferences.getString("user_name1", "")
////              topicEditText.setText(saveUserName1)
////      typeTextEdit.setText(saveUserName1)
//
//
//        val button =
//            findViewById<ImageView>(R.id.user)   // there is a issue, check this tomorrow
//        button.setOnClickListener {
//            val intent = Intent(this, User_detail::class.java)
//            startActivity(intent)
//        }
//        val button2 = findViewById<TextView>(R.id.user2)
//        button2.setOnClickListener {
//            val intent = Intent(this, User_detail::class.java)
//            startActivity(intent)
//        }
//
//
//        val textView = findViewById<TextView>(R.id.user3)
//        textView.text = saveUserName1
////        textView.setOnClickListener {
////            val intent = Intent(this, SignUp_pg::class.java)
////            startActivity(intent)
////        }
//
//        val button3 = findViewById<ImageView>(R.id.menu)
//        button3.setOnClickListener {
//            val intent = Intent(this, Menu_pg::class.java)
//            startActivity(intent)
//        }
//
//        val button4 = findViewById<Button>(R.id.Add_list)
//        button4.setOnClickListener {
//            val intent = Intent(this, Add_list::class.java)
//            startActivity(intent)
//        }
//
////        val button5 = findViewById<Button>(R.id.Demo)
////        button5.text = saveTopic
////        button5.setOnClickListener {
////            val intent = Intent(this, Demo_pg::class.java)
////            startActivity(intent)
////        }
//
//
//        Log.d("Home.pg", "Saved User: $saveUserName1")
//
//
////        println("Test >>>>>>>>>>>>>>>>>>>>>>$saveTopic")
//        println("Test >>>>>>>>>>>>>>>>>>>11>>>>>$saveUserName1")
//        Log.d("Home_pg", "onCreate() called")
//
////
//
//
//        if (!taskString.isNullOrEmpty()) {
//            val taskList = taskString.split(",")
//
//            for (topic in taskList) {
//                if (topic.isNotEmpty()) {
//                    createButton(topic, container)
//                }
//            }
//        }
//    }


    @SuppressLint("ResourceAsColor")
    private fun createButton(topicName: String, container: GridView) {
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
                intent.putExtra("TOPIC_KEY", topicName)
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
                deleteTopic(topicName)
            }
        }

        stack.addView(newBtn)
        stack.addView(deleteBtn)
        container.addView(stack)
    }
//
//    val deleteBtn = android.widget.ImageButton(this).apply {
//        setImageResource(R.drawable.delete_730)
//        background = null
//
//        setBackgroundColor(android.graphics.Color.YELLOW)
//
//        setColorFilter(android.graphics.Color.RED)
//        layoutParams = FrameLayout.LayoutParams(200, 100).apply {
//            gravity = android.view.Gravity.TOP or android.view.Gravity.END
//            setMargins(0, 20, 20, 0)
//        }
//
//        setOnClickListener {
//            deleteTopic(topicName)
//        }
//    }
//
//    stack.addView(newBtn)
//    stack.addView(deleteBtn)
//    container.addView(stack)


    private fun deleteTopic(topicToDelete: String) {
        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val taskString = sharedPreferences.getString("task_list", "")
        val taskTopic = sharedPreferences.getString("content_$topicToDelete", "")

        if (!taskString.isNullOrEmpty()) {
            val taskList = taskString.split(",").toMutableList()

            taskList.remove(topicToDelete)

            val newListString = taskList.joinToString(",")

            val editor = sharedPreferences.edit()
            editor.putString("task_list", newListString)
            editor.remove("content_$topicToDelete")
            editor.putString("content_", newListString)
            editor.apply()

            editor.putString("content_", newListString)

            Toast.makeText(this, "Deleted $topicToDelete", Toast.LENGTH_SHORT).show()
            refreshButtons()
        }
    }


}

class GridViewAdapter(

    private val context: Context,

    private val taskList: MutableList<String>,

    private val onDeleteClick: (String) -> Unit,

    private val onItemClick: (String) -> Unit

) : android.widget.BaseAdapter() {

    override fun getCount(): Int = taskList.size

    override fun getItem(position: Int): Any = taskList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("ViewHolder", "ResourceAsColor")

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val topicName = taskList[position]

        // 1. Create the Container (FrameLayout)

        val stack = FrameLayout(context).apply {

            layoutParams = AbsListView.LayoutParams(

                AbsListView.LayoutParams.MATCH_PARENT,

                300 // Set a fixed height for grid items

            )

            setPadding(20, 20, 20, 20)

        }

        // 2. Create the Main Task Button

        val newBtn = Button(context).apply {

            text = topicName

            isAllCaps = false

            textSize = 20f

            setTextColor(Color.BLACK)

            setBackgroundResource(R.drawable.todo_bg)

            layoutParams = FrameLayout.LayoutParams(

                FrameLayout.LayoutParams.MATCH_PARENT,

                FrameLayout.LayoutParams.MATCH_PARENT

            )

            setOnClickListener { onItemClick(topicName) }

        }

        // 3. Create the Delete Button (X)

        val deleteBtn = Button(context).apply {

            text = "X"

            textSize = 18f

            setTextColor(Color.RED)

            setBackgroundColor(Color.TRANSPARENT)

            layoutParams = FrameLayout.LayoutParams(120, 100).apply {

                gravity = android.view.Gravity.TOP or android.view.Gravity.END

            }

            setOnClickListener { onDeleteClick(topicName) }

        }

        stack.addView(newBtn)

        stack.addView(deleteBtn)

        return stack

    }

}










