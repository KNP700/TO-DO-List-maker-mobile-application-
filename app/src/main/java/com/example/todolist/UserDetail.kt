package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class UserDetail : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private lateinit var username: TextView
    private lateinit var firstname: TextView
    private lateinit var lastname: TextView
    private lateinit var eMail: TextView
    private lateinit var phone : TextView
    private lateinit var address : TextView
//    private lateinit var phone: TextView
//    private lateinit var address: TextView
////    private lateinit var username2: TextView
////    private lateinit var eMail2: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_user_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        username = findViewById(R.id.text_uname)
        firstname = findViewById(R.id.fname)
        lastname = findViewById(R.id.lname)
        address = findViewById(R.id.address)
//            fName = findViewById(R.id.fname)
//            lName = findViewById(R.id.lname)
        eMail = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
//        eMail = findViewById(R.id.phone)
//            address = findViewById(R.id.address)
//        username2 = findViewById(R.id.userN)
//        eMail2 = findViewById(R.id.eMail)

        getUsername()  //get

//
//        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
//        val saveUserName1 = sharedPreferences.getString("user_name1", "")
//        val saveFname = sharedPreferences.getString("fname", "")
//        val saveLname = sharedPreferences.getString("lname", "")
//        val saveEmail = sharedPreferences.getString("email", "")
//        val savePhone = sharedPreferences.getString("phone","")
//        val saveAddress = sharedPreferences.getString("address","")
//
//        val saveUserName2 = sharedPreferences.getString("user_name1", "")
//        val saveEmail2 = sharedPreferences.getString("email", "")


//        Log.d("User_detail", "user_name : $saveUserName1")

        val button = findViewById<ImageView>(R.id.close)
        button.setOnClickListener {
            finish()
        }
//        val textView = findViewById<TextView>(R.id.text_uname)
//        textView.text = saveUserName1
////        val textView4 = findViewById<TextView>(R.id.text_uname)
////        textView4.text = saveUserName2
//
//
//        val textView1 = findViewById<TextView>(R.id.fname)
//        textView1.text = saveFname
//
//
//        val textView2 = findViewById<TextView>(R.id.lname)
//        textView2.text = saveLname
//
//
//        val textView3 = findViewById<TextView>(R.id.email)
//        textView3.text = saveEmail
////        val textView6 =findViewById<TextView>(R.id.eMail)
////        textView6.text = saveEmail2
//        val textView4 = findViewById<TextView>(R.id.phone)
//        textView4.text=savePhone
//
//        val textView5 = findViewById<TextView>(R.id.address)
//        textView5.text=saveAddress

        findViewById<Button>(R.id.Update).setOnClickListener {
            val intent = Intent(this, UserDetailUpdate::class.java)
            startActivityForResult(intent, 101)
        }
    }

    private fun getUsername() {
        try {
            val user = auth.currentUser
            if (user != null) {
                db.collection("users").document(user.uid).get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            val nameFromDb = document.getString("username")
                            val namefromDb2 = document.getString("firstName")
                            val namefromDb3 = document.getString("lastName")
                            val namefromDb4 = document.getString("email")
                            val namefromDb5 = document.getString("phone")
                            val namefromDb6 = document.getString("address")
                            username.text = nameFromDb
                            firstname.text = namefromDb2
                            lastname.text = namefromDb3
                            eMail.text = namefromDb4
                            phone.text = namefromDb5
                            address.text = namefromDb6



                        }
                    }
                    .addOnFailureListener {
                        Log.d("User_detail", "Failed")
                    }
            }
        } catch (e: Exception) {
            print(e)
        }
    }

    //    @Deprecated("This method has been deprecated in favor of using the Activity Result API")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("TAG", "onActivityResult: $requestCode")
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Log.i("TAG", "onActivityResult: its a hit")
                Toast.makeText(this, "Sucessfully saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomePage::class.java)
                startActivity(intent)
                finish()

//            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "User cancelled", Toast.LENGTH_SHORT).show()
            }
//
        }
    }
}
