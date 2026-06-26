package com.example.unipool

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignUpAsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_as)

        findViewById<Button>(R.id.btn_student).setOnClickListener {
            startActivity(Intent(this, SignUpStudentActivity::class.java))
        }

        findViewById<Button>(R.id.btn_staff).setOnClickListener {
            startActivity(Intent(this, SignUpStaffActivity::class.java))
        }

        findViewById<Button>(R.id.btn_driver).setOnClickListener {
            startActivity(Intent(this, SignUpDriverActivity::class.java))
        }
    }
}
