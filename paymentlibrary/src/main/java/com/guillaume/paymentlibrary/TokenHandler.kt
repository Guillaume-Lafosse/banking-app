package com.guillaume.paymentlibrary

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


class CardTokenHandler {
    companion object {
        private lateinit var sharedPreferences: SharedPreferences

        fun storeCardToken(context: Context, key: String, token: String) {
            openSharedPreferences(context)
            sharedPreferences.edit().putString(key, token).apply()
        }

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

        fun getCardToken(context: Context, key: String): String {
            openSharedPreferences(context)
            if (sharedPreferences.getString(key, null) == null) {
                throw NoFoundCardTokenException()
            }
            return sharedPreferences.getString(key, null)!!
        }
    }
}