package com.guillaume.bankingapp.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.guillaume.bankingapp.R
import com.guillaume.bankingapp.databinding.FragmentPinVerificationBinding

class PinVerificationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentPinVerificationBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pin_verification, container, false)
        binding.pinVerificationInputText.requestFocus()
        binding.pinVerificationInputText.addTextChangedListener {
            if (it!!.length == 6) {
                val action =
                    PinVerificationFragmentDirections.actionPinVerificationFragmentToPaymentBankVerificationFragment(
                        pin = binding.pinVerificationInputText.text.toString()
                    )
                findNavController().navigate(action)
            }
        }
        return binding.root
    }
}