package org.hyperskill.secretdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.hyperskill.secretdiary.databinding.ActivityLoginBinding
import org.hyperskill.secretdiary.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener {
            println(binding.etPin.text)
            if (binding.etPin.text.toString() == "1234") {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else binding.etPin.setError("Wrong PIN!")
        }
    }
}