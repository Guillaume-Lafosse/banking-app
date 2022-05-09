package com.guillaume.bankingapp.home

import android.nfc.NfcAdapter
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.guillaume.bankingapp.R.layout.activity_main
import com.guillaume.bankingapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var nfcAdapter: NfcAdapter? = null
    private val MIME_TEXT_PLAIN = "text/plain"
    private val TAG = "NfcDemo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, activity_main)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SECURE
        )
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}


