package com.example.unipool

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvSignUpLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername   = findViewById(R.id.et_username)
        etEmail      = findViewById(R.id.et_email)
        etPassword   = findViewById(R.id.et_password)
        btnLogin     = findViewById(R.id.btn_login)
        tvSignUpLink = findViewById(R.id.tv_signup_link)

        btnLogin.setOnClickListener { attemptLogin() }

        tvSignUpLink.setOnClickListener {
            startActivity(Intent(this, SignUpAsActivity::class.java))
        }
    }

    private fun attemptLogin() {
        val username = etUsername.text.toString().trim()
        val email    = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        if (username.isBlank()) { etUsername.error = "Username is required"; return }
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Enter a valid email"; return
        }
        if (password.isBlank()) { etPassword.error = "Password is required"; return }

        val role = detectRole(username)
        Toast.makeText(this, "Logged in as $role", Toast.LENGTH_SHORT).show()

        // TODO: navigate to your dashboard here
    }

    private fun detectRole(username: String): String {
        return when {
            username.startsWith("STU", ignoreCase = true) -> "Student"
            username.startsWith("STA", ignoreCase = true) -> "Staff"
            username.startsWith("DRI", ignoreCase = true) -> "Driver"
            username.startsWith("A", ignoreCase = true)   -> "Admin"
            else -> "User"
        }
    }
}