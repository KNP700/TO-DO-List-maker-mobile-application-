package com.example.todolist

import android.app.ComponentCaller
import android.content.Intent
import android.health.connect.datatypes.units.TemperatureDelta
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class User_detail : AppCompatActivity() {

    private lateinit var username1: TextView
    private lateinit var fName: TextView
    private lateinit var lName: TextView
    private lateinit var eMail: TextView
    private lateinit var phone : TextView
    private lateinit var address : TextView
//    private lateinit var username2: TextView
//    private lateinit var eMail2: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_user_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        username1 = findViewById(R.id.user3)
        fName = findViewById(R.id.fname)
        lName = findViewById(R.id.lname)
        eMail = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        address = findViewById(R.id.address)
//        username2 = findViewById(R.id.userN)
//        eMail2 = findViewById(R.id.eMail)

        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val saveUserName1 = sharedPreferences.getString("user_name1", "")
        val saveFname = sharedPreferences.getString("fname", "")
        val saveLname = sharedPreferences.getString("lname", "")
        val saveEmail = sharedPreferences.getString("email", "")
        val savePhone = sharedPreferences.getString("phone","")
        val saveAddress = sharedPreferences.getString("address","")

        val saveUserName2 = sharedPreferences.getString("user_name1", "")
        val saveEmail2 = sharedPreferences.getString("email", "")


        Log.d("User_detail", "user_name : $saveUserName1")

        val button = findViewById<ImageView>(R.id.close)
        button.setOnClickListener {
            finish()
        }
        val textView = findViewById<TextView>(R.id.text_uname)
        textView.text = saveUserName1
//        val textView4 = findViewById<TextView>(R.id.text_uname)
//        textView4.text = saveUserName2


        val textView1 = findViewById<TextView>(R.id.fname)
        textView1.text = saveFname


        val textView2 = findViewById<TextView>(R.id.lname)
        textView2.text = saveLname


        val textView3 = findViewById<TextView>(R.id.email)
        textView3.text = saveEmail
//        val textView6 =findViewById<TextView>(R.id.eMail)
//        textView6.text = saveEmail2
        val textView4 = findViewById<TextView>(R.id.phone)
        textView4.text=savePhone

        val textView5 = findViewById<TextView>(R.id.address)
        textView5.text=saveAddress

        val button2 = findViewById<Button>(R.id.Update)
        button2.setOnClickListener {
            val intent = Intent(this, Update_detail_pg::class.java)
//            startActivity(intent)
            startActivityIfNeeded(intent, 101)

        }

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        Log.d("TAG", "onActivityResult: $requestCode")
        if(requestCode == 101) {
            if(resultCode == RESULT_OK) {
                Log.i("TAG", "onActivityResult: its a hit")
                Toast.makeText(this,"Sucessfully saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Home_pg::class.java)
                startActivity(intent)
                // read updated data from preference
                // update the ui.
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "User cancelled", Toast.LENGTH_SHORT).show()
            }
//           finish()
        }
        finish()
    }

}

