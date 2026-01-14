package com.example.todolist

import android.content.ContentValues.TAG
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
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"

    //    lateinit var mGoogleSignInClient : GoogleSignInCl
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var auth: FirebaseAuth

    fun isLoggedIn(): Boolean {
        if (firebaseAuth.currentUser != null) {
            print(tag + "already logged In")
            return true
        }
        return false

    }

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
        setContentView(R.layout.activity_main)
//       enableEdgeToEdge()
        auth = Firebase.auth
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
                Toast.makeText(this@MainActivity, "Press back again to exit", Toast.LENGTH_SHORT)
                    .show()

                Handler(Looper.getMainLooper()).postDelayed({
                    backPressedOnce = false
                }, 2000)
            }
        })


        usernameEditText = findViewById(R.id.Username_box)
        passwordEditText = findViewById(R.id.password_box)


        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val savedName = sharedPreferences.getString("user_name1", "")
        val passwordConfirm = sharedPreferences.getString("password", "")


//        println("Test >>>>>>>>>>>>>>>>>>>>>>")
// usernameEditText.setText(savedName)
        //passwordEditText.setText(passwordConfirm)

        Log.d("MainActivity", "Username : $savedName, $passwordConfirm")


        val forgotPassButton = findViewById<Button>(R.id.button)
        forgotPassButton.setOnClickListener {
            val intent = Intent(this, Forgot_Pass::class.java)
            startActivity(intent)
        }

        val signUpButton = findViewById<Button>(R.id.signup)
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUp_pg::class.java)
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.button2)
        loginButton.setOnClickListener {

            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()


            if (username == savedName && password == passwordConfirm) {
                println("Test >>>>>>>>>>>>>>>>>>>>>> $passwordConfirm")

                val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putBoolean("isLoggedIn", true) //check this
                editor.apply()

                val intent = Intent(this, Home_pg::class.java)
                startActivity(intent)
                finish()
            } else {
                Log.d("MainActivity", "Login Failed")
                android.widget.Toast.makeText(
                    this,
                    "Incorrect Username or Password. Try again",
                    android.widget.Toast.LENGTH_SHORT
                ).show()


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
                // Your server's client ID, not your Android client ID.
                .setServerClientId(getString(R.string.default_web_client_id))
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(false)
                .build()


            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            coroutineScope {
                try {
                    val result = credentialManager.getCredential(
                        request = request,
                        context = this@MainActivity,
                    )
                    handleSignIn(result)
                } catch (e: GetCredentialException) {
                    // Handle failure
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

                // AUTHENTICATE WITH FIREBASE
                firebaseAuthWithGoogle(idToken)

            } catch (e: GoogleIdTokenParsingException) {
                Log.e(TAG, "Received an invalid google id token response", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    updateUI(null)
                }
            }
    }

//    fun handleSignIn(result: GetCredentialResponse) {
//       try {// Handle the successfully returned credential.
//            val credential = result.credential
//            val responseJson: String
//
//            when (credential) {
//
//                // Passkey credential
//                is PublicKeyCredential -> {
//                    // Share responseJson such as a GetCredentialResponse to your server to validate and
//                    // authenticate
//                    responseJson = credential.authenticationResponseJson
//                }
//
//                // Password credential
//                is PasswordCredential -> {
//                    // Send ID and password to your server to validate and authenticate.
//                    val username = credential.id
//                    val password = credential.password
//                }
//
//                // GoogleIdToken credential
//                is CustomCredential -> {
//                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
//                        try {
//                            // Use googleIdTokenCredential and extract the ID to validate and
//                            // authenticate on your server.
//                            val googleIdTokenCredential = GoogleIdTokenCredential
//                                .createFrom(credential.data)
//                            // You can use the members of googleIdTokenCredential directly for UX
//                            // purposes, but don't use them to store or control access to user
//                            // data. For that you first need to validate the token:
//                            // pass googleIdTokenCredential.getIdToken() to the backend server.
//                            // see [validation instructions](https://developers.google.com/identity/gsi/web/guides/verify-google-id-token)
//                        } catch (e: GoogleIdTokenParsingException) {
//                            Log.e(TAG, "Received an invalid google id token response", e)
//                        }
//                    } else {
//                        // Catch any unrecognized custom credential type here.
//                        Log.e(TAG, "Unexpected type of credential")
//                    }
//                }
//
//                else -> {
//                    // Catch any unrecognized credential type here.
//                    Log.e(TAG, "Unexpected type of credential")
//                }
//            }
//        }catch (e: Exception){
//           Log.e("",e.toString())
//       }
//    }

//        if (usernameEditText == savedName && passwordEditText == passwordConfirm) {
//
//
//            Log.d("MainActivity", "Login Successful")
//            val intent = Intent(this, Home_pg::class.java)
//            startActivity(intent)
//            finish()
//
//        } else {
//
//
//            Log.d("MainActivity", "Login Failed")
//
//            android.widget.Toast.makeText(this, "Incorrect Username or Password", android.widget.Toast.LENGTH_SHORT).show()
//        }


//        println("Test >>>>>>>>>>>>>>>>>>>>>>")
//        Log.d("MainActivity", "onCreate() called")


}
