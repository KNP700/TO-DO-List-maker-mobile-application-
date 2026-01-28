package com.example.todolist

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

class Update_detail_pg : AppCompatActivity() {

    //    private lateinit var username: EditText
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    private lateinit var fName: TextView
    private lateinit var lName: TextView

    //    private lateinit var e_mail: EditText
    private lateinit var phone: TextView
    private lateinit var address: TextView
//    private lateinit var oPassword: EditText
//    private lateinit var nPassword: EditText
//    private lateinit var cPassword: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        try {
            setContentView(R.layout.activity_update_detail_pg)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            auth = Firebase.auth
//        username = findViewById(R.id.userN)
//        e_mail = findViewById(R.id.eMail)
            phone = findViewById(R.id.phone)
            address = findViewById(R.id.address)
//        nPassword = findViewById(R.id.nPass)
//        oPassword = findViewById(R.id.oPass)
            // cPassword = findViewById(R.id.cPass)
            fName = findViewById(R.id.fName1)
            lName = findViewById(R.id.lName1)
//            phone =findViewById(R.id.phone)


            getUsername()
//            updateData(fName.text.toString(), lName.text.toString())
//            loadData()
//        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
//        val savePhone =  sharedPreferences.getString("phone", "")
//        val saveAddress = sharedPreferences.getString("address", "")
//        val saveFname = sharedPreferences.getString("fname", "")
//        val saveLname = sharedPreferences.getString("lname", "")


//            val savePass = sharedPreferences.getString("pass", "")

//        val editText1 = findViewById<EditText>(R.id.phone)
//        editText1.setText(savePhone)
//
//        val editText2 = findViewById<EditText>(R.id.fName1)
//        editText2.setText(saveFname)
//
//        val editText3 = findViewById<EditText>(R.id.lName1)
//        editText3.setText(saveLname)
//
//        val editText4 = findViewById<EditText>(R.id.address)
//        editText4.setText(saveAddress)


            val button3 = findViewById<ImageView>(R.id.close)
            button3.setOnClickListener {
                finish()
            }

            val button4 = findViewById<Button>(R.id.Cancel)
            button4.setOnClickListener {
                //            val intent = Intent(this, User_detail::class.java)
                //            startActivity(intent)
                setResult(RESULT_CANCELED)
                finish()
            }


            val button = findViewById<Button>(R.id.Save)
            button.setOnClickListener {
                val updateFirstname = fName.text.toString()
                val updateLastname = lName.text.toString()
                val updatePhone = phone.text.toString()
                val updateAddress= address.text.toString()
//
                if (updateFirstname.isEmpty()) {
                    fName.error = "First name cant be empty"
                    return@setOnClickListener
                }
                if (updateLastname.isEmpty()) {
                    lName.error = "Last name cant be empty"
                    return@setOnClickListener
                }



                lifecycleScope.launch {

                    updateData(updateFirstname,updateLastname,updatePhone,updateAddress)

                }

                //finish()
            }
        } catch (e: Exception) {
            print(e.toString())
        }
    }


    private fun getUsername() {
        try {
            val user = auth.currentUser
            if (user != null) {
                db.collection("users").document(user.uid).get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
//                            val nameFromDb = document.getString("username")
                            val namefromDb2 = document.getString("firstName")
                            val namefromDb3 = document.getString("lastName")
                            val namefromDb4 = document.getString("phone")
                            val namefromD5 = document.getString("address")
//                            username.text = nameFromDb
                            lName.text = namefromDb3
                            fName.text = namefromDb2
                            phone.text = namefromDb4
                            address.text = namefromD5

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

    private fun loadData() {
        try {
            val user = auth.currentUser
            if (user != null) {
                db.collection("todos").document(user.uid).get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            val firstName = document.getString("firstName")
                            val lastName = document.getString("lastName")
                            val phone = document.getString("phone")
                            val address = document.getString("address")


                        }
                    }
            }
        } catch (e: Exception) {
            print(e.toString())
        }
    }


    private fun updateData(firstName: String, lastName: String, phone:String, address:String) {
        try {
            val user = auth.currentUser

            if (user != null) {
                val updates = mapOf(

                    "firstName" to firstName,
                    "lastName" to lastName,
                    "phone" to phone,
                    "address" to address

                )
                db.collection("users").document(user.uid).update(updates)
                    .addOnSuccessListener {
                        Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show()
                        setResult(RESULT_OK)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                    }

            } else {
                Toast.makeText(this, "User not logged", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            print(e.toString())
        }


    }
}


////            val editor = sharedPreferences.edit()
////            val username1 = username.text.toString()
////            val eMail1 = e_mail.text.toString()
//            val phone = phone.text.toString()
//            val address = address.text.toString()
////            val oPass1 = oPassword.text.toString()
////            val nPass1 = nPassword.text.toString()
////            val cPass1 = cPassword.text.toString()
//            val fName = fName.text.toString()
//            val lName = lName.text.toString()
//
//            updateUserData(
//                editor,
//                phone,
//                address,
//                fName,
//                lName
//            )

//
//fun updateUserData(
//    editor: SharedPreferences.Editor,
////        userName: String,
////        eMail: String,
//    phone: String,
//    address: String,

////        oPass: String,
////        nPass: String,
////        cPass: String,
//    fName: String,
//    lName: String
//) {
////        editor.putString("user_name1", userName)
////        editor.apply()
////        editor.putString("email", eMail)
////        editor.apply()
//    editor.putString("phone", phone)
//    editor.apply()
//    editor.putString("address", address)
//    editor.apply()
////        editor.putString("oPass", oPass)
////        editor.apply()
////        editor.putString("nPass", nPass)
////        editor.apply()
////        editor.putString("cPass", cPass)
////        editor.apply()
//    editor.putString("fname", fName)
//    editor.apply()
//    editor.putString("lname", lName)
//    editor.apply()
////
//
////        val intent = Intent(this, Home_pg::class.java)
////        startActivity(intent)
//    setResult(RESULT_OK)
//    finish()
//
//}



