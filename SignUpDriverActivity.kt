package com.example.unipool

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SignUpDriverActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etShuttleId: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var cbTerms: CheckBox
    private lateinit var tvTermsLink: TextView
    private lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_driver)

        etUsername        = findViewById(R.id.et_username)
        etEmail           = findViewById(R.id.et_email)
        etShuttleId       = findViewById(R.id.et_shuttle_id)
        etPassword        = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
        cbTerms           = findViewById(R.id.cb_terms)
        tvTermsLink       = findViewById(R.id.tv_terms_link)
        btnSignUp         = findViewById(R.id.btn_sign_up)

        tvTermsLink.setOnClickListener { showTermsDialog() }
        btnSignUp.setOnClickListener { attemptSignUp() }
    }

    private fun showTermsDialog() {
        val sheet = TermsBottomSheetFragment()
        sheet.onAccepted = { cbTerms.isChecked = true }
        sheet.show(supportFragmentManager, TermsBottomSheetFragment.TAG)
    }

    private fun attemptSignUp() {
        val username  = etUsername.text.toString().trim()
        val email     = etEmail.text.toString().trim()
        val shuttleId = etShuttleId.text.toString().trim()
        val password  = etPassword.text.toString()
        val confirm   = etConfirmPassword.text.toString()

        val usernameResult = SignUpValidator.validateUsername(username)
        if (!usernameResult.isValid) { etUsername.error = usernameResult.errorMessage; return }

        val emailResult = SignUpValidator.validateEmail(email)
        if (!emailResult.isValid) { etEmail.error = emailResult.errorMessage; return }

        val shuttleResult = SignUpValidator.validateShuttleId(shuttleId)
        if (!shuttleResult.isValid) { etShuttleId.error = shuttleResult.errorMessage; return }

        val passwordResult = SignUpValidator.validatePassword(password)
        if (!passwordResult.isValid) { etPassword.error = passwordResult.errorMessage; return }

        val confirmResult = SignUpValidator.validateConfirmPassword(password, confirm)
        if (!confirmResult.isValid) { etConfirmPassword.error = confirmResult.errorMessage; return }

        if (!cbTerms.isChecked) {
            Toast.makeText(this, "Please agree to the Terms and Conditions", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, "Driver account created!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}