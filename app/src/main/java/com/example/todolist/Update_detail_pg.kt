package com.example.todolist

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Update_detail_pg : AppCompatActivity() {

    //    private lateinit var username: EditText
    private lateinit var fName: EditText
    private lateinit var lName: EditText

    //    private lateinit var e_mail: EditText
    private lateinit var phone: EditText
    private lateinit var address: EditText
//    private lateinit var oPassword: EditText
//    private lateinit var nPassword: EditText
//    private lateinit var cPassword: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_update_detail_pg)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        username = findViewById(R.id.userN)
//        e_mail = findViewById(R.id.eMail)
        phone = findViewById(R.id.phone)
        address = findViewById(R.id.address)
//        nPassword = findViewById(R.id.nPass)
//        oPassword = findViewById(R.id.oPass)
        // cPassword = findViewById(R.id.cPass)
        fName = findViewById(R.id.fName1)
        lName = findViewById(R.id.lName1)


        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val savePhone =  sharedPreferences.getString("phone", "")
        val saveAddress = sharedPreferences.getString("address", "")
        val saveFname = sharedPreferences.getString("fname", "")
        val saveLname = sharedPreferences.getString("lname", "")


//            val savePass = sharedPreferences.getString("pass", "")

        val editText1 = findViewById<EditText>(R.id.phone)
        editText1.setText(savePhone)

        val editText2 = findViewById<EditText>(R.id.fName1)
        editText2.setText(saveFname)

        val editText3 = findViewById<EditText>(R.id.lName1)
        editText3.setText(saveLname)

        val editText4 = findViewById<EditText>(R.id.address)
        editText4.setText(saveAddress)


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
            val editor = sharedPreferences.edit()
//            val username1 = username.text.toString()
//            val eMail1 = e_mail.text.toString()
            val phone = phone.text.toString()
            val address = address.text.toString()
//            val oPass1 = oPassword.text.toString()
//            val nPass1 = nPassword.text.toString()
//            val cPass1 = cPassword.text.toString()
            val fName = fName.text.toString()
            val lName = lName.text.toString()

            updateUserData(
                editor,
                phone,
                address,
                fName,
                lName
            )
        }
    }

    fun updateUserData(
        editor: SharedPreferences.Editor,
//        userName: String,
//        eMail: String,
        phone: String,
        address: String,
//        oPass: String,
//        nPass: String,
//        cPass: String,
        fName: String,
        lName: String
    ) {
//        editor.putString("user_name1", userName)
//        editor.apply()
//        editor.putString("email", eMail)
//        editor.apply()
        editor.putString("phone", phone)
        editor.apply()
        editor.putString("address", address)
        editor.apply()
//        editor.putString("oPass", oPass)
//        editor.apply()
//        editor.putString("nPass", nPass)
//        editor.apply()
//        editor.putString("cPass", cPass)
//        editor.apply()
        editor.putString("fname", fName)
        editor.apply()
        editor.putString("lname", lName)
        editor.apply()
//

//        val intent = Intent(this, Home_pg::class.java)
//        startActivity(intent)
        setResult(RESULT_OK)
        finish()

    }


}
