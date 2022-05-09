package com.guillaume.bankingapp.card_details

data class CardDetails(
    val id: Int,
    val pan: String,
    val nickname: String,
    val van: String,
    val is_default_payment_method: String
)