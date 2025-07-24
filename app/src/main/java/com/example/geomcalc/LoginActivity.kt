package com.example.geomcalc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (validateCredentials(email, password)) {
                // Успешный вход → Переход в MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Закрываем LoginActivity
            } else {
                Toast.makeText(this, "Ошибка: неверные данные", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Функция проверки логина и пароля
    private fun validateCredentials(email: String, password: String): Boolean {
        return email == "user@example.com" && password == "123456"
    }
}
