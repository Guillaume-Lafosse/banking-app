package com.guillaume.paymentlibrary

import android.content.Context
import android.content.SharedPreferences
import android.text.format.DateFormat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.util.*

class TransactionHandler {
    companion object {
        private lateinit var sharedPreferences: SharedPreferences

        private fun openSharedPreferences(context: Context) {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            sharedPreferences = EncryptedSharedPreferences.create(
                context,
                "card_tokens",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

        fun addTransaction(context: Context) {
            openSharedPreferences(context)
            val currentDate = Calendar.getInstance().time
            val dateFormat: java.text.DateFormat? =
                DateFormat.getDateFormat(context)
            val timeFormat: java.text.DateFormat? =
                DateFormat.getTimeFormat(context)
            val transactionSet: MutableSet<String> = HashSet()
            val storedTransactionSet = sharedPreferences.getStringSet("transactions", null)
            if (storedTransactionSet == null) {
                transactionSet.add(
                    "Transaction: ${dateFormat?.format(currentDate)} ${
                        timeFormat?.format(
                            currentDate
                        )
                    }"
                )
                sharedPreferences.edit().putStringSet("transactions", transactionSet).apply()
            } else {
                transactionSet.addAll(storedTransactionSet)
                transactionSet.add(
                    "Transaction: ${dateFormat?.format(currentDate)} ${
                        timeFormat?.format(
                            currentDate
                        )
                    }"
                )
                sharedPreferences.edit().putStringSet("transactions", transactionSet).apply()
            }
        }

        fun getTransactions(context: Context): List<String> {
            openSharedPreferences(context)
            val storedTransactionSet = sharedPreferences.getStringSet("transactions", null)
            return storedTransactionSet?.toList() ?: listOf()
        }
    }
}