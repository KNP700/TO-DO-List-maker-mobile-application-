package com.example.todolist

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.coroutines.Job
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomePage : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    private var backPressedOnce = false
    private lateinit var userName: TextView
    private lateinit var container: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        auth = Firebase.auth

        userName = findViewById(R.id.user3)

        container = findViewById(R.id.buttonContainer)


        getUserName()


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedOnce) {
                    finishAffinity()
                    return
                }
                backPressedOnce = true
                Toast.makeText(this@HomePage, "Press back again to exit", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    backPressedOnce = false
                }, 2000)
            }
        })

        val addListBtn = findViewById<Button>(R.id.Add_list)
        addListBtn.setOnClickListener {
            val intent = Intent(this, AddList::class.java)
            startActivity(intent)
        }
        val userBtn = findViewById<ImageView>(R.id.user)
        userBtn.setOnClickListener { startActivity(Intent(this, UserDetail::class.java)) }

        val userTxt = findViewById<TextView>(R.id.user2)
        userTxt.setOnClickListener { startActivity(Intent(this, UserDetail::class.java)) }

        val menuBtn = findViewById<ImageView>(R.id.menu)
        menuBtn.setOnClickListener { startActivity(Intent(this, MenuPage::class.java)) }
    }


    private fun getUserName() {
        lifecycleScope.launch {
            val homeProgress = findViewById<ProgressBar>(R.id.homeProgress)
            try {
                val user = auth.currentUser
                if (user != null) {



                    homeProgress.visibility = View.VISIBLE

                    val document = db.collection("users")
                        .document(user.uid)
                        .get()
                        .await()


                    if (document.exists()) {
                        val nameFromDb = document.getString("username")
                        userName.text = nameFromDb
                    }
                }
            } catch (e: Exception) {

                Log.d("Home_pg", "Failed to fetch user data: ${e.message}")
                e.printStackTrace()
            } finally {
                homeProgress.visibility = View.GONE
            }
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

        container.removeAllViews()

        val orientation = resources.configuration.orientation
        container.columnCount = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 2 else 1

        val currentUser = auth.currentUser

        if (currentUser != null) {

            db.collection("todos")
                .whereEqualTo("user_uid", currentUser.uid)

                .get()
                .addOnSuccessListener { result ->
                    container.removeAllViews()

                    if (result.isEmpty) {
                        Toast.makeText(this, "No tasks found", Toast.LENGTH_SHORT).show()
                    }

                    for (document in result) {
                        val docId = document.id
                        val title = document.getString("title") ?: "No Title"
                        createButton(docId, title, container)
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show()
                    Log.e("Home_pg", "Error fetching data", exception)
                }
        }
    }


    @SuppressLint("ResourceAsColor")
    private fun createButton(idStr: String, title: String, container: GridLayout) {
//        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)

//
//        val topicName =
//            sharedPreferences.getString("title_$idStr", "No Title") ?: "No Title"

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
            text = title
            isAllCaps = false
            textSize = 30f
            setTextColor(getColor(R.color.black))
            setBackgroundResource(R.drawable.todo_bg)
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )

            setOnClickListener {

                val intent = Intent(context, ChangeToDoList::class.java)
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
                deleteTopic(idStr)
            }
        }
        stack.addView(newBtn)
        stack.addView(deleteBtn)
        container.addView(stack)
    }

    private fun deleteTopic(idToDelete: String) {

        db.collection("todos").document(idToDelete)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                refreshButtons()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error deleting", Toast.LENGTH_SHORT).show()
            }

//    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
//    refreshButtons()
    }
}

