package com.guillaume.bankingapp.network

import com.guillaume.bankingapp.card_details.CardDetails
import com.guillaume.paymentlibrary.PaymentToken
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


private const val BASE_URL = "https://aqueous-journey-83087.herokuapp.com"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface BackendApiService {
    @GET("card")
    fun getCard():
            Call<CardDetails>

    @POST("enroll")
    fun enroll(@Body pan: RequestBody):
            Call<PaymentToken>

    @POST("pay")
    fun pay(@Body pan: RequestBody):
            Call<String>

    @GET("cards")
    fun getCards():
            Call<List<CardDetails>>

    @POST("card/delete")
    fun removeCard(@Body id: RequestBody):
            Call<String>
}

object BackendApi {
    val retrofitService: BackendApiService by lazy {
        retrofit.create(BackendApiService::class.java)
    }
}