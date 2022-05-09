package com.guillaume.bankingapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.guillaume.bankingapp.R
import com.guillaume.bankingapp.card_details.CardDetails
import com.guillaume.bankingapp.databinding.FragmentCardListBinding
import com.guillaume.bankingapp.network.BackendApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardListFragment : Fragment() {

    private lateinit var binding: FragmentCardListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_card_list, container, false)
        binding.cardsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.addNewCardBtn.setOnClickListener {
            findNavController()
                .navigate(R.id.action_newCardFragment_to_enrollCardActivity)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        updateCardList()
    }

    private fun updateCardList() {
        BackendApi.retrofitService.getCards().enqueue(object : Callback<List<CardDetails>> {
            override fun onResponse(
                call: Call<List<CardDetails>>,
                response: Response<List<CardDetails>>
            ) {
                binding.cardsList.adapter = CardListAdapter(response.body()!!.toTypedArray())
            }

            override fun onFailure(call: Call<List<CardDetails>>, t: Throwable) {
                Toast.makeText(
                    requireActivity(),
                    "An error occured while connecting to the backend",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}

