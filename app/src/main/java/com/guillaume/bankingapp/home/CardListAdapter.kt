package com.guillaume.bankingapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.guillaume.bankingapp.R
import com.guillaume.bankingapp.card_details.CardDetails

class CardListAdapter(private val dataSet: Array<CardDetails>) :
    RecyclerView.Adapter<CardListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val panTextView: TextView
        val nicknameTextView: TextView

        init {
            panTextView = view.findViewById(R.id.panTextView)
            nicknameTextView = view.findViewById(R.id.nicknameTextView)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.panTextView.text = dataSet[position].pan
        viewHolder.nicknameTextView.text = dataSet[position].nickname
        viewHolder.itemView.setOnClickListener {
            val bundle = bundleOf(
                "id" to dataSet[position].id,
                "pan" to dataSet[position].pan,
                "nickname" to dataSet[position].nickname,
                "isDefaultPaymentMethod" to dataSet[position].is_default_payment_method,
                "van" to dataSet[position].van
            )
            it.findNavController()
                .navigate(R.id.action_newCardFragment_to_cardDetailsActivity, bundle)
        }
    }

    override fun getItemCount() = dataSet.size

}