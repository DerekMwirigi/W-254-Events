package com.example.wsupevents.ui.tickets

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wsupevents.R
import com.example.wsupevents.utils.OnRecyclerViewItemClick
import java.lang.ref.WeakReference

class TicketItemViewHolder(itemView: View, listener: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView), View.OnClickListener,
    View.OnLongClickListener {

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(listener)

    var itemVew: View
    var label: TextView
    var description: TextView
    var image: ImageView
    var tvMonthYear: TextView
    var tvDay: TextView
    var tvPaymentStatus: TextView
    init {
        this.itemVew = itemView
        label = itemView.findViewById(R.id.tvLabel)
        description = itemView.findViewById(R.id.tvDesc)
        tvMonthYear = itemView.findViewById(R.id.tvMonthYear)
        tvDay = itemView.findViewById(R.id.tvDay)
        tvPaymentStatus = itemView.findViewById(R.id.tvPaymentStatus)
        image = itemView.findViewById(R.id.imgImage)

        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    override fun onLongClick(p0: View?): Boolean {
        listenerWeakReference.get()?.onLongClickListener(adapterPosition)
        return true
    }
    override fun onClick(v: View?) {
        listenerWeakReference.get()?.onClickListener(adapterPosition)
    }
}