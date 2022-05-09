package com.guillaume.bankingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.guillaume.bankingapp.databinding.FragmentBankVerificationBinding
import com.guillaume.bankingapp.network.BackendApi
import com.guillaume.paymentlibrary.CardTokenHandler
import com.guillaume.paymentlibrary.NoFoundCardTokenException
import com.guillaume.paymentlibrary.PaymentToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BankVerificationFragment : Fragment() {

    private lateinit var binding: FragmentBankVerificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bank_verification, container, false)
        lifecycleScope.launch {
            delay(4000)
            if (arguments?.getString("context") == "enrollment") {
                enroll()
            } else {
                pay()
            }
        }
        return binding.root
    }

    private fun enroll() {
        val panArg = arguments?.getString("pan")!!
        val nicknameArg = arguments?.getString("nickname")!!
        val panObject = JSONObject("{pan: '${panArg}', nickname: '${nicknameArg}'}")
        val wrappedJSON =
            RequestBody.create(MediaType.parse("application/json"), panObject.toString())
        BackendApi.retrofitService.enroll(wrappedJSON)
            .enqueue(object : Callback<PaymentToken> {
                override fun onResponse(
                    call: Call<PaymentToken>,
                    response: Response<PaymentToken>
                ) {
                    if (response.code() == 200) {

                        val action =
                            BankVerificationFragmentDirections.actionEnrollBankVerificationFragmentToSuccessfulEnrollment(
                                panArg.substring(panArg.length - 4),
                                nicknameArg
                            )
                        CardTokenHandler.storeCardToken(
                            requireActivity(),
                            "card",
                            response.body()?.token!!
                        )
                        findNavController().navigate(action)
                    }
                }

                override fun onFailure(call: Call<PaymentToken>, t: Throwable) {
                    Toast.makeText(
                        requireActivity(),
                        "An error has occurred while enrollment",
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.action_enrollBankVerificationFragment_to_mainActivity2)
                }
            })
    }

    private fun pay() {
        val pinArg = arguments?.getString("pin")!!
        try {

            val pinObject = JSONObject(
                "{pin: ${pinArg}, token: ${
                    CardTokenHandler.getCardToken(
                        requireActivity(),
                        "card"
                    )
                }}"
            )
            val wrappedJSON =
                RequestBody.create(MediaType.parse("application/json"), pinObject.toString())
            BackendApi.retrofitService.pay(wrappedJSON).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.code() == 200 && response.body() == "completed") {
                        findNavController().navigate(R.id.action_paymentBankVerificationFragment_to_successTransactionFragment)
                    } else {
                        findNavController().navigate(R.id.action_paymentBankVerificationFragment_to_failedTransactionFragment)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        requireActivity(),
                        "An error has occurred while payment",
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.action_paymentBankVerificationFragment_to_mainActivity)
                }
            })
        } catch (error: NoFoundCardTokenException) {
            Toast.makeText(
                requireActivity(),
                "No card token found. Please remove and enroll your card again.",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_paymentBankVerificationFragment_to_mainActivity)
        }
    }
}