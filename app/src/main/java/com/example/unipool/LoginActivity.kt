package com.example.unipool

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.unipool.network.LoginRequest
import com.example.unipool.network.LoginResponse
import com.example.unipool.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Enter a valid email"
            return
        }

        if (password.isBlank()) {
            etPassword.error = "Password is required"
            return
        }

        val request = LoginRequest(email, password)
        
        RetrofitClient.instance.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val user = response.body()?.user
                    if (user != null) {
                        Toast.makeText(this@LoginActivity, "Logged in as ${user.role}", Toast.LENGTH_SHORT).show()
                        navigateToDashboard(user.role)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = try {
                        com.google.gson.JsonParser.parseString(errorBody).asJsonObject.get("message").asString
                    } catch (e: Exception) {
                        "Invalid credentials"
                    }
                    Toast.makeText(this@LoginActivity, "Login failed: $message", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Network Error: ${t.message}", Toast.LENGTH_LONG).show()
                android.util.Log.e("LoginActivity", "Error: ", t)
            }
        })
    }

    private fun navigateToDashboard(role: String) {
        when (role) {
            "Driver" -> {
                startActivity(Intent(this, DriverHomeActivity::class.java))
                finish()
            }
            "Student", "Staff", "Admin" -> {
                Toast.makeText(this, "$role Dashboard Coming Soon", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
