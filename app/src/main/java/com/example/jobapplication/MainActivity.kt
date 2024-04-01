package com.example.jobapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)


        val startNow : Button = findViewById(R.id.startnowbtn)

        startNow.setOnClickListener {
            Intent(this@MainActivity, SignInActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}