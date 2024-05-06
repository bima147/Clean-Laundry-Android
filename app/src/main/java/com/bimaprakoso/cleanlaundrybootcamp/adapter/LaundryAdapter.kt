package com.bimaprakoso.cleanlaundrybootcamp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bimaprakoso.cleanlaundrybootcamp.R
import com.bimaprakoso.cleanlaundrybootcamp.domain.response.TransactionResponse

class LaundryAdapter (
    private val items: MutableList<LaundryItem>,
) : RecyclerView.Adapter<LaundryAdapter.LaundryViewHolder>() {

    fun addItems(newItems: List<LaundryItem>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaundryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return LaundryViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaundryViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class LaundryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTransactionUUID: TextView = itemView.findViewById(R.id.tvLaundryUUID)

        fun bind(item: LaundryItem) {
            tvTransactionUUID.text = item.transactionUUID
        }

//        init {
//            btnBook.setOnClickListener {
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    val bookingItem = book[position]
//                    onBookClick(bookingItem)
//                }
//            }
//        }
    }

    fun removeItem(transactionResponse: LaundryItem) {
        val position = items.indexOf(transactionResponse)
        if (position != -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clearData() {
        items.clear()
        notifyDataSetChanged()
    }
}