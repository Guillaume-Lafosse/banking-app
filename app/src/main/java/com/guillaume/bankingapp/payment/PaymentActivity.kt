package com.guillaume.bankingapp.payment

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.guillaume.bankingapp.R.layout.activity_payment
import com.guillaume.bankingapp.databinding.ActivityPaymentBinding
import com.guillaume.paymentlibrary.NfcHandler

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, activity_payment)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SECURE
        )
        supportActionBar?.setDisplayShowTitleEnabled(false)
        NfcHandler(this, intent, lifecycle)
    }

}