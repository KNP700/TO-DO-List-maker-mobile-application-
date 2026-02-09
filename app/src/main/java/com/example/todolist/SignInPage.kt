package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.example.services.SharedprefService
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SignInPage : AppCompatActivity() {

    private val tag = "MainActivity"

    //    lateinit var mGoogleSignInClient : GoogleSignInCl
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var prefService: SharedprefService

//    fun isLoggedIn(): Boolean {
//        if (firebaseAuth.currentUser != null) {
//            print(tag + "already logged In")
//            return true
//        }
//        return false
//
//    }

//    suspend fun register(
//        username : String , password : String
//    ): Boolean{
//        try{
//
//            val result = suspendCoroutine { continuation ->
//                firebaseAuth.createUserWithEmailAndPassword(username,password)
//                    .addOnSuccessListener {
//                        println(tag+"register success")
//                    }
//                    .addOnFailureListener {
//                        println(tag+ "register failure")
//                        continuation
//                    }
//
//            }
//        } catch (e:Exception){
//            e.printStackTrace()
//            if (e is CancellationException) throw e
//            println(tag+"register exception ${e. message}")
//            return false
//        }
//
//    }


    private var backPressedOnce = false
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_page)
//       enableEdgeToEdge()
        prefService = SharedprefService(this)
        auth = Firebase.auth


        val currentUser = auth.currentUser
        if (currentUser != null || prefService.isLoggedIn()) {
            startActivity(Intent(this, HomePage::class.java))
            finish()
        }

        setOnApplyWindowInsetsListener(findViewById(R.id.view_loging)) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            v.updatePadding(
                left = bars.left,
                top = bars.top,
                right = bars.right,
                bottom = bars.bottom,
            )
            WindowInsetsCompat.CONSUMED
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedOnce) {
                    finishAffinity()
                    return
                }

                backPressedOnce = true
                Toast.makeText(this@SignInPage, "Press back again to exit", Toast.LENGTH_SHORT)
                    .show()

                Handler(Looper.getMainLooper()).postDelayed({
                    backPressedOnce = false
                }, 2000)
            }
        })


        usernameEditText = findViewById(R.id.Username_box)
        passwordEditText = findViewById(R.id.password_box)


//        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
//        val savedName = sharedPreferences.getString("user_name1", "")
//        val passwordConfirm = sharedPreferences.getString("password", "")
//
//
////        println("Test >>>>>>>>>>>>>>>>>>>>>>")
//// usernameEditText.setText(savedName)
//        //passwordEditText.setText(passwordConfirm)
//
//        Log.d("MainActivity", "Username : $savedName, $passwordConfirm")


        val forgotPassButton = findViewById<Button>(R.id.button)
        forgotPassButton.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

        val signUpButton = findViewById<Button>(R.id.signup)
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpPage::class.java)
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.button2)
        loginButton.setOnClickListener {


            val emailInput = usernameEditText.text.toString()
            val passwordInput = passwordEditText.text.toString()

            if (emailInput.isEmpty()) {
                usernameEditText.error = "Please Enter Your Email"
                return@setOnClickListener
            }

            if (passwordInput.isEmpty()) {
                passwordEditText.error = "Please Enter Your Password"
                return@setOnClickListener
            }
            3
            auth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(tag, "signInWithEmail:success")

                        Toast.makeText(baseContext, "Login Successful!", Toast.LENGTH_SHORT).show()

                        prefService.setLoggedIn(true)

                        val intent = Intent(this, HomePage::class.java)
////                        val checkPrefs = getSharedPreferences("UserPreferences", MODE_PRIVATE)
////                        val editor = checkPrefs.edit()
////                        editor.putBoolean("isLoggedIn", true)
//                        editor.apply()
                        startActivity(intent)
                        finish()
                    } else {

                        Log.w(tag, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Invalid Email or Password:",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

//            if (username == savedName && password == passwordConfirm) {
//                println("Test >>>>>>>>>>>>>>>>>>>>>> $passwordConfirm")

//                    val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
//                    val editor = sharedPref.edit()
//
//                    FirebaseAuth.getInstance().signOut()
//                    editor.putBoolean("isLoggedIn", true)
//                    editor.apply()

//
//                editor.putBoolean("isLoggedIn", true) //check this
//                editor.apply()
//
//                val intent = Intent(this, Home_pg::class.java)
//                startActivity(intent)
//                finish()
//            } else {
//                Log.d("MainActivity", "Login Failed")
//                android.widget.Toast.makeText(
//                    this,
//                    "Incorrect Username or Password. Try again",
//                    android.widget.Toast.LENGTH_SHORT
//                ).show()

                }
        }

        val googleLoginButton = findViewById<Button>(R.id.button3)
        googleLoginButton.setOnClickListener {
            lifecycleScope.launch {
                gLogin()
            }

//            lifecycleScope.launch {
//                try {
//                    val result = credentialManager.getCredential(
//                        context = this@MainActivity,
//                        request = request
//                    )
//                    println(result)
//                    handleSignIn(result) // You will create this function to handle the login success
//                } catch (e: Exception) {
//                    println(e)
//                    // Handle login errors here
//                }

//                private fun handleSignIn(credential: Credential) {


        }


    }

    suspend fun gLogin() {
        try {

            val credentialManager = CredentialManager.create(this)

            val googleIdOption = GetGoogleIdOption.Builder()

                .setServerClientId(getString(R.string.default_web_client_id))

                .setFilterByAuthorizedAccounts(false)
                .build()


            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            coroutineScope {
                try {
                    val result = credentialManager.getCredential(
                        request = request,
                        context = this@SignInPage,
                    )
                    handleSignIn(result)
                } catch (e: GetCredentialException) {

                }
            }
        } catch (e: Exception) {
            Log.e("", e.toString())
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        val credential = result.credential

        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            try {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken

                firebaseAuthWithGoogle(idToken)

            } catch (e: GoogleIdTokenParsingException) {
                Log.e(tag, "Received an invalid google id token response", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Log.d(tag, "signIn success")
                    val user = auth.currentUser


                    val intent = Intent(this, HomePage::class.java)
                    startActivity(intent)
                    finish()

                } else {

                    Log.w(tag, "signIn:fail", task.exception)
                    Toast.makeText(this, "Google Sign In Failed", Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }
            }
    }

}