package com.example.wsupevents.ui.tickets

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wsupevents.R
import com.example.wsupevents.utils.OnCartItemClickEvent
import java.lang.ref.WeakReference

class TicketTypeViewHolder (itemView: View, listener: OnCartItemClickEvent) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val listenerWeakReference: WeakReference<OnCartItemClickEvent> = WeakReference(listener)
    var itemVew: View
    var tvLabel: TextView
    var tvPrice: TextView
    var tvMinus: TextView
    var tvCount: TextView
    var tvAdd: TextView
    var tvClossesOn: TextView
    init {
        this.itemVew = itemView
        tvLabel = itemView.findViewById(R.id.tvLabel)
        tvPrice = itemView.findViewById(R.id.tvPrice)
        tvMinus = itemView.findViewById(R.id.tvMinus)
        tvCount = itemView.findViewById(R.id.tvCount)
        tvAdd = itemView.findViewById(R.id.tvAdd)
        tvClossesOn = itemView.findViewById(R.id.tvClossesOn)

        tvAdd.setOnClickListener(this)
        tvMinus.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (p0 == tvAdd)
            listenerWeakReference.get()?.addItem(adapterPosition)
        if (p0 == tvMinus)
            listenerWeakReference.get()?.minusItem(adapterPosition)
    }
}