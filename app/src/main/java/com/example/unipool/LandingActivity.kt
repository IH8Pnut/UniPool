package com.example.unipool

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        // Using a safe call (?.) ensures that if the ID is missing, the app won't crash!
        val signUpButton = findViewById<Button>(R.id.btn_sign_up_now)

        if (signUpButton != null) {
            signUpButton.setOnClickListener {
                startActivity(Intent(this, SignUpAsActivity::class.java))
            }
        } else {
            // This will warn you if the ID in your XML layout doesn't match!
            Toast.makeText(this, "Error: Could not find button 'btn_sign_up_now' in layout", Toast.LENGTH_LONG).show()
        }
    }
}