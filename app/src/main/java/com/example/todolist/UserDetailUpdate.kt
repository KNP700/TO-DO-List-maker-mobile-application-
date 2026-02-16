package com.example.todolist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlin.text.matches

class UserDetailUpdate : AppCompatActivity() {

    //    private lateinit var username: EditText
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    private lateinit var fName: TextView
    private lateinit var lName: TextView

    //    private lateinit var e_mail: EditText
    private lateinit var phone: TextView
    private lateinit var address: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

                showUpdateSave()
            }
        } catch (e: Exception) {

        }
    }

    private fun showUpdateSave() {

        val updateFirstname = fName.text.toString()
        val updateLastname = lName.text.toString()
        val updatePhone = phone.text.toString()
        val updateAddress = address.text.toString()

        var isValid = true

        if (updateFirstname.isEmpty()) {
            fName.error = "First name cant be empty"
            return
        } else if (!updateFirstname.matches("^[a-zA-Z\\s]{2,50}$".toRegex())) {
            fName.error = "Use letters only"
            fName.requestFocus()
            isValid = false
        } else if (updateFirstname.length > 20) {
            fName.error = "Type Your first name within 20 letters"
            fName.requestFocus()
            isValid = false
        }

        if (updateLastname.isEmpty()) {
            lName.error = "Last name cant be empty"
            return
        } else if (!updateLastname.matches("^[a-zA-Z\\s]{2,50}$".toRegex())) {
            lName.error = "Use letters only"
            lName.requestFocus()
            isValid = false
        } else if (updateLastname.length > 20) {
            lName.error = "Type Your first name within 20 letters"
            lName.requestFocus()
            isValid = false
        }

        if (!updatePhone.isEmpty()) {
            if (!updatePhone.matches("-?\\d+(\\.\\d+)?".toRegex())) {
                phone.error = "Use numbers only"
                phone.requestFocus()
                isValid = false
            } else if (updatePhone.length > 11) {
                phone.error = "Enter valid number"
                phone.requestFocus()
                isValid = false
            } else if (!updatePhone.startsWith(prefix = "07")) {
                phone.error = "Enter Valid number"
                phone.requestFocus()
                isValid = false
            }
        }

        if (isValid) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Update")
            builder.setMessage("Are you sure you want to update?")

            builder.setPositiveButton("yes") setOnClickListener@{ dialog, which ->
                lifecycleScope.launch {
                    updateData(updateFirstname, updateLastname, updatePhone, updateAddress)
                }
            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()


        }

//finish()
        getUsername()
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


    private fun updateData(
        firstName: String,
        lastName: String,
        phone: String,
        address: String
    ) {
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


