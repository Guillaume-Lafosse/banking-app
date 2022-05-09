package com.guillaume.bankingapp.enrollment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.guillaume.bankingapp.R
import com.guillaume.bankingapp.databinding.FragmentSuccessfulEnrollmentBinding

class SuccessfulEnrollmentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentSuccessfulEnrollmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_successful_enrollment, container, false
        )
        binding.successfulEnrollmentBtn.setOnClickListener {
            activity?.finish()
        }
        binding.panTextView.text = arguments?.getString("enrolledPan")
        binding.nicknameTextView.text = arguments?.getString("nickname")
        return binding.root
    }
}