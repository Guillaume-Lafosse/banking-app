package com.guillaume.bankingapp.enrollment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.guillaume.bankingapp.R
import com.guillaume.bankingapp.databinding.FragmentEnrollBinding
import com.guillaume.paymentlibrary.CardNumberValidator

class EnrollmentFormFragment : Fragment() {

    private lateinit var binding: FragmentEnrollBinding
    private lateinit var formLayouts: List<TextInputLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_enroll, container, false
        )
        formLayouts = listOf(
            binding.cardNumberTextInputLayout,
            binding.cvcInputLayout,
            binding.expirationDateInputLayout,
            binding.nameTextInputLayout,
            binding.addressTextInputLayout,
            binding.postalCodeTextInputLayout,
            binding.cityTextInputLayout,
            binding.phoneNumberTextInputLayout,
        )
        setFormFieldListeners()
        binding.enrollSaveBtn.setOnClickListener {
            if (isFormValid()) {
                val action =
                    EnrollmentFormFragmentDirections.actionEnrollFragmentToEnrollBankVerificationFragment(
                        pan = binding.cardNumberTextInput.text.toString(),
                        nickname = binding.nicknameTextInput.text.toString()
                    )
                findNavController()
                    .navigate(action)
            }
        }
        return binding.root
    }

    private fun isFormValid(): Boolean {
        return formLayouts.all {
            it.error == null && !TextUtils.isEmpty(it.editText?.text)
        }
    }

    private fun setFormFieldListeners() {

        binding.cardNumberTextInputLayout.editText?.addTextChangedListener {
            if (TextUtils.isEmpty(it)) {
                binding.cardNumberTextInputLayout.error = "Required field"
            } else {
                if (it?.length!! < 16) {
                    binding.cardNumberTextInputLayout.error = "Invalid card number"

                } else if (!CardNumberValidator.isValid(it.toString())) {
                    binding.cardNumberTextInputLayout.error = "Invalid card number"
                } else {
                    binding.cardNumberTextInputLayout.error = null
                }
            }
        }
        for (i in 1 until formLayouts.size) {
            formLayouts[i].editText?.addTextChangedListener {
                if (TextUtils.isEmpty(it)) {
                    formLayouts[i].error = "Required field"
                } else {
                    formLayouts[i].error = null
                }
            }
        }
    }

}