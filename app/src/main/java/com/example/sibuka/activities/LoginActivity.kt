package com.example.sibuka.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sibuka.MainActivity
import com.example.sibuka.databinding.ActivityLoginBinding
import com.example.sibuka.utils.FirebaseUtils

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setupClickListeners()
            Log.d(TAG, "LoginActivity created successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate: ${e.message}", e)
            Toast.makeText(this, "Error loading login page", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupClickListeners() {
        try {
            binding.btnLogin.setOnClickListener {
                loginAdmin()
            }

            binding.tvRegister.setOnClickListener {
                navigateToRegister()
            }

            Log.d(TAG, "Click listeners setup successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up click listeners: ${e.message}", e)
            Toast.makeText(this, "Error setting up UI", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToRegister() {
        try {
            Log.d(TAG, "Navigating to RegisterActivity")
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Error navigating to register: ${e.message}", e)
            Toast.makeText(this, "Error opening register page: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun loginAdmin() {
        try {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Mohon isi email dan password", Toast.LENGTH_SHORT).show()
                return
            }

            binding.btnLogin.isEnabled = false
            binding.btnLogin.text = "Logging in..."

            FirebaseUtils.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.btnLogin.isEnabled = true
                    binding.btnLogin.text = "Login"

                    if (task.isSuccessful) {
                        Log.d(TAG, "Login successful")
                        Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        val errorMessage = task.exception?.message ?: "Login failed"
                        Log.e(TAG, "Login failed: $errorMessage")
                        Toast.makeText(this, "Login gagal: $errorMessage", Toast.LENGTH_LONG).show()
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "Error in loginAdmin: ${e.message}", e)
            binding.btnLogin.isEnabled = true
            binding.btnLogin.text = "Login"
            Toast.makeText(this, "Error during login: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "LoginActivity destroyed")
    }
}
