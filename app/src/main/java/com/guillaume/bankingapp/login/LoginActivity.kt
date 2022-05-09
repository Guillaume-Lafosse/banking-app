package com.guillaume.bankingapp.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.guillaume.bankingapp.R
import com.guillaume.bankingapp.databinding.ActivityLoginBinding
import com.guillaume.bankingapp.home.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SECURE
        )
        binding.loginBtn.setOnClickListener {
            if (!TextUtils.isEmpty(binding.passwordTextInput.text) && !TextUtils.isEmpty(binding.usernameTextView.text)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}