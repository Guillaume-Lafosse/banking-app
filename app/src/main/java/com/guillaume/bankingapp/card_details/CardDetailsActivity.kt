package com.guillaume.bankingapp.card_details

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.guillaume.bankingapp.R
import com.guillaume.bankingapp.databinding.ActivityCardDetailsBinding
import com.guillaume.bankingapp.network.BackendApi
import com.guillaume.paymentlibrary.TransactionHandler
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CardDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardDetailsBinding
    private val viewModel: CardDetailsViewModel by lazy {
        ViewModelProvider(this).get(CardDetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_card_details)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SECURE
        )
        binding.setLifecycleOwner { lifecycle }
        binding.viewModel = viewModel
        setCardInfoValues()
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val transactions = TransactionHandler.getTransactions(this)

        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, transactions)

        binding.transactionList.adapter = arrayAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.card_details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.removeCard -> {
                removeCard()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun removeCard(): Boolean {
        val panObject = JSONObject("{id: ${intent?.getIntExtra("id", 0)}}")
        val wrappedJSON =
            RequestBody.create(MediaType.parse("application/json"), panObject.toString())
        BackendApi.retrofitService.removeCard(wrappedJSON)
            .enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.code() == 200) {
                        Toast.makeText(
                            this@CardDetailsActivity,
                            "Card has been successfully removed !",
                            Toast.LENGTH_LONG
                        ).show()
                        this@CardDetailsActivity.finish()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        this@CardDetailsActivity,
                        "An error has occurred while removing card",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        return true

    }

    private fun setCardInfoValues() {
        binding.panTextView.text = intent?.getStringExtra("pan")
        binding.nicknameTextView.text = intent?.getStringExtra("nickname")
        binding.defaultPaymentTextView.text = intent?.getStringExtra("isDefaultPaymentMethod")
        binding.vanTextView.text = intent?.getStringExtra("van")
    }
}