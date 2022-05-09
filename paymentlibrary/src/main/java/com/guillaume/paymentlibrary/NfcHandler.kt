package com.guillaume.paymentlibrary

import android.app.Activity
import android.content.Intent
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.widget.Toast
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import java.io.UnsupportedEncodingException
import java.util.*

data class NfcHandler(
    val activity: Activity,
    val intent: Intent,
    val activityLifecycle: Lifecycle
) :
    DefaultLifecycleObserver {

    private var nfcAdapter: NfcAdapter? = null
    private val MIME_TEXT_PLAIN = "text/plain"
    private var tagContent: String? = null

    init {
        activityLifecycle.addObserver(this)
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
        if (nfcAdapter == null) {
            Toast.makeText(activity, "This device doesn't support NFC.", Toast.LENGTH_LONG).show()
            activity.finish()
        }
        if (!nfcAdapter!!.isEnabled) {
            Toast.makeText(activity, "NFC is disabled.", Toast.LENGTH_LONG).show()
            activity.finish()
        }
        try {
            handleIntent(intent)
        } catch (error: UnsupportedEncodingException) {
            Toast.makeText(activity, "Unsupported encoding.", Toast.LENGTH_LONG).show()
        } catch (error: WrongMimeTypeException) {
            Toast.makeText(activity, "Wrong Mime type.", Toast.LENGTH_LONG).show()
        } catch (error: Throwable) {
            Toast.makeText(activity, "Error while reading tag.", Toast.LENGTH_LONG).show()
        }
    }

    //Intercept intent and redirect to tag reader if tag is compatible
    @Throws(WrongMimeTypeException::class)
    private fun handleIntent(intent: Intent) {
        val action = intent.action
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val type = intent.type
            if (MIME_TEXT_PLAIN == type) {
                val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
                if (tag != null) {
                    tagContent = TagReader(tag).readTag()
                }
            } else {
                throw WrongMimeTypeException()
            }
        }
    }


/*
  Using of lifecycle class for our NFC interaction

  private fun setupForegroundDispatch() {
        val intent = Intent(activity, javaClass)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0)
        val filter = IntentFilter()
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
        filter.addCategory(Intent.CATEGORY_DEFAULT)
        try {
            filter.addDataType(MIME_TEXT_PLAIN)
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            throw RuntimeException("Check your mime type.")
        }
        nfcAdapter?.enableForegroundDispatch(activity, pendingIntent, arrayOf(filter), null)
    }

    private fun stopForegroundDispatch() {
        nfcAdapter?.disableForegroundDispatch(activity)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        setupForegroundDispatch()
    }

    override fun onPause(owner: LifecycleOwner) {
        stopForegroundDispatch()
        super.onPause(owner)
    }*/
}

private data class TagReader(val tag: Tag) {

    fun readTag(): String? {
        val ndef = Ndef.get(tag)
            ?: null
        val ndefMessage = ndef?.cachedNdefMessage
        val records = ndefMessage?.records
        if (records != null) {
            for (ndefRecord in records) {
                if (ndefRecord.tnf == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(
                        ndefRecord.type,
                        NdefRecord.RTD_TEXT
                    )
                ) {
                    return readText(ndefRecord)
                }
            }
        }
        return null
    }

    @Throws(UnsupportedEncodingException::class)
    private fun readText(record: NdefRecord): String {
        return String(record.payload)
    }
}