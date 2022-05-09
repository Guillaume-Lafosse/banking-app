package com.guillaume.bankingapp.card_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guillaume.bankingapp.network.BackendApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//Exemple if we want to use view model combined with live data
class CardDetailsViewModel : ViewModel() {
    private val _response = MutableLiveData<CardDetails>()
    val response: LiveData<CardDetails>
        get() = _response

    init {
        getCardDetailsProperties()
    }

    private fun getCardDetailsProperties() {
        BackendApi.retrofitService.getCard().enqueue(object : Callback<CardDetails> {
            override fun onResponse(call: Call<CardDetails>, response: Response<CardDetails>) {
                _response.value = response.body()
            }

            override fun onFailure(call: Call<CardDetails>, t: Throwable) {

            }
        })
    }

}