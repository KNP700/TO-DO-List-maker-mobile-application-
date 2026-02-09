//package com.example.todolist
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.util.Log
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.activity.OnBackPressedCallback
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener
//import androidx.core.view.WindowInsetsCompat
//import androidx.core.view.updatePadding
//import androidx.credentials.CredentialManager
//import androidx.credentials.CustomCredential
//import androidx.credentials.GetCredentialRequest
//import androidx.credentials.GetCredentialResponse
//import androidx.credentials.exceptions.GetCredentialException
//import androidx.lifecycle.lifecycleScope
//import com.example.services.ShareprefService // Import your service
//import com.google.android.libraries.identity.googleid.GetGoogleIdOption
//import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
//import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
//import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
//import com.google.firebase.Firebase
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.GoogleAuthProvider
//import com.google.firebase.auth.auth
//import kotlinx.coroutines.coroutineScope
//import kotlinx.coroutines.launch
//
//class MainActivity : AppCompatActivity() {
//
//    private val tag = "MainActivity"
//    private lateinit var auth: FirebaseAuth
//    private lateinit var prefService: ShareprefService // 1. Declare Service
//
//    private var backPressedOnce = false
//    private lateinit var nameEditText: EditText
//    private lateinit var passwordEditText: EditText
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // 2. Initialize Service and Firebase
//        prefService = ShareprefService(this)
//        auth = Firebase.auth
//
//        // Check if already logged in via Firebase or SharedPreferences
//        val currentUser = auth.currentUser
//        if (currentUser != null || prefService.isLoggedIn()) {
//            navigateToHome()
//        }
//
//        setupWindowInsets()
//        setupBackButtonHandler()
//
//        usernameEditText = findViewById(R.id.Username_box)
//        passwordEditText = findViewById(R.id.password_box)
//
//        // Navigation Buttons
//        findViewById<Button>(R.id.button).setOnClickListener {
//            startActivity(Intent(this, Forgot_Pass::class.java))
//        }
//
//        findViewById<Button>(R.id.signup).setOnClickListener {
//            startActivity(Intent(this, SignUp_pg::class.java))
//        }
//
//        // Email/Password Login Logic
//        val loginButton = findViewById<Button>(R.id.button2)
//        loginButton.setOnClickListener {
//            val emailInput = usernameEditText.text.toString().trim()
//            val passwordInput = passwordEditText.text.toString().trim()
//
//            if (emailInput.isEmpty()) {
//                usernameEditText.error = "Please Enter Your Email"
//                return@setOnClickListener
//            }
//            if (passwordInput.isEmpty()) {
//                passwordEditText.error = "Please Enter Your Password"
//                return@setOnClickListener
//            }
//
//            auth.signInWithEmailAndPassword(emailInput, passwordInput)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        Log.d(tag, "signInWithEmail:success")
//
//                        // 3. Update preferences through service
//                        prefService.setLoggedIn(true)
//
//                        Toast.makeText(baseContext, "Login Successful!", Toast.LENGTH_SHORT).show()
//                        navigateToHome()
//                    } else {
//                        Log.w(tag, "signInWithEmail:failure", task.exception)
//                        Toast.makeText(baseContext, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
//                    }
//                }
//        }
//
//        // Google Login Button
//        findViewById<Button>(R.id.button3).setOnClickListener {
//            lifecycleScope.launch {
//                gLogin()
//            }
//        }
//    }
//
//    private fun navigateToHome() {
//        val intent = Intent(this, Home_pg::class.java)
//        startActivity(intent)
//        finish()
//    }
//
//    private fun setupWindowInsets() {
//        setOnApplyWindowInsetsListener(findViewById(R.id.view_loging)) { v, insets ->
//            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
//            v.updatePadding(left = bars.left, top = bars.top, right = bars.right, bottom = bars.bottom)
//            WindowInsetsCompat.CONSUMED
//        }
//    }
//
//    private fun setupBackButtonHandler() {
//        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                if (backPressedOnce) {
//                    finishAffinity()
//                    return
//                }
//                backPressedOnce = true
//                Toast.makeText(this@MainActivity, "Press back again to exit", Toast.LENGTH_SHORT).show()
//                Handler(Looper.getMainLooper()).postDelayed({ backPressedOnce = false }, 2000)
//            }
//        })
//    }
//
//    // --- Google Sign In Logic ---
//
//    suspend fun gLogin() {
//        try {
//            val credentialManager = CredentialManager.create(this)
//            val googleIdOption = GetGoogleIdOption.Builder()
//                .setServerClientId(getString(R.string.default_web_client_id))
//                .setFilterByAuthorizedAccounts(false)
//                .build()
//
//            val request = GetCredentialRequest.Builder()
//                .addCredentialOption(googleIdOption)
//                .build()
//
//            coroutineScope {
//                try {
//                    val result = credentialManager.getCredential(request = request, context = this@MainActivity)
//                    handleSignIn(result)
//                } catch (e: GetCredentialException) {
//                    Log.e(tag, "Credential Manager Error", e)
//                }
//            }
//        } catch (e: Exception) {
//            Log.e(tag, "Google Login Error", e)
//        }
//    }
//
//    private fun handleSignIn(result: GetCredentialResponse) {
//        val credential = result.credential
//        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
//            try {
//                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
//                firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
//            } catch (e: GoogleIdTokenParsingException) {
//                Log.e(tag, "Invalid google id token", e)
//            }
//        }
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    Log.d(tag, "Google signIn success")
//
//                    // 4. Update preferences for Google login too
//                    prefService.setLoggedIn(true)
//
//                    navigateToHome()
//                } else {
//                    Log.w(tag, "Google signIn:fail", task.exception)
//                    Toast.makeText(this, "Google Sign In Failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//}




//



//
//fun saveUserName(name: String) {
//    sharedPref.edit().putString("user_name", name).apply()
//}