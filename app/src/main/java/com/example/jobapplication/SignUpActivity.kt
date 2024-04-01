package com.example.jobapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var signUpButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var cnfPasswordEditText: EditText
    private lateinit var googleSignInButton: ImageView
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        signUpButton = findViewById(R.id.SignUpbtn)
        emailEditText = findViewById(R.id.EmailEditText)
        passwordEditText = findViewById(R.id.PasswordEditText)
        usernameEditText = findViewById(R.id.UsernameEditText)
        cnfPasswordEditText = findViewById(R.id.CnfPasswordEditText)
        googleSignInButton = findViewById(R.id.googleicon)

        signUpButton.setOnClickListener {
            signUp()
        }

        googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }

        val login: Button = findViewById(R.id.Loginbtn)
        login.setOnClickListener {
            Intent(this@SignUpActivity, SignInActivity::class.java).also {
                startActivity(it)
            }
        }

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signUp() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val username = usernameEditText.text.toString()
        val cnfPassword = cnfPasswordEditText.text.toString()

        if (validateInputs(email, password, username, cnfPassword)) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign up success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                        Intent(this@SignUpActivity,SignInActivity::class.java).also{
                            startActivity(it)
                        }
                    } else {
                        // If sign up fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI accordingly
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(
                    baseContext,
                    "Google sign in failed: ${e.statusCode}",
                    Toast.LENGTH_SHORT
                ).show()
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
                    updateUI(user)
                    Intent(this@SignUpActivity,ProfileActivity::class.java).also{
                        startActivity(it)
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun validateInputs(
        email: String,
        password: String,
        username: String,
        cnfPassword: String
    ): Boolean {
        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || cnfPassword.isEmpty()) {
            Toast.makeText(
                baseContext,
                "All fields must be filled",
                Toast.LENGTH_SHORT
            ).show()

            return false
        } else if (password != cnfPassword) {
            Toast.makeText(
                baseContext,
                "Passwords do not match",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

                                                                                                                                                                                                                                                                                          // You can add more specific validation logic as needed

        return true
    }

    private fun updateUI(user: FirebaseUser?) {
        // Add your UI update logic here
    }

    private fun reload() {
        // Add reload logic if needed
    }

    companion object {
        private const val TAG = "SignUpActivity"
        private const val RC_SIGN_IN = 9001
    }
}
