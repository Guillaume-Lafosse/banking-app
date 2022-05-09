package com.guillaume.bankingapp.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.guillaume.bankingapp.R
import com.guillaume.bankingapp.databinding.FragmentFailedTransactionBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FailedTransactionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentFailedTransactionBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_failed_transaction,
            container,
            false
        )
        lifecycleScope.launch {
            delay(3000)
            findNavController().navigate(R.id.action_failedTransactionFragment_to_mainActivity)
        }
        return binding.root
    }
}