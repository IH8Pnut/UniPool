package com.example.unipool

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This instantly forwards your app to Claude's custom LandingActivity
        val intent = Intent(this, LandingActivity::class.java)
        startActivity(intent)
        finish()
    }
}