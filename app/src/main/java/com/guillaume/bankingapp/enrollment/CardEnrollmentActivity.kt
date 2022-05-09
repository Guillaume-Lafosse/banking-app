package com.guillaume.bankingapp.enrollment

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.guillaume.bankingapp.R
import com.guillaume.bankingapp.databinding.ActivityEnrollCardBinding

class CardEnrollmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnrollCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enroll_card)
        //We set the flag secure to prevent sensitive info leaks
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SECURE
        )
        //Remove title in our action bar
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}