package com.example.wsupevents.ui.tickets

import android.view.View
import android.widget.ImageView
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
    var imgMinus: ImageView
    var tvCount: TextView
    var imgAdd: ImageView
    var tvClossesOn: TextView
    init {
        this.itemVew = itemView
        tvLabel = itemView.findViewById(R.id.tvLabel)
        tvPrice = itemView.findViewById(R.id.tvPrice)
        imgMinus = itemView.findViewById(R.id.imgMinus)
        tvCount = itemView.findViewById(R.id.tvCount)
        imgAdd = itemView.findViewById(R.id.imgAdd)
        tvClossesOn = itemView.findViewById(R.id.tvClossesOn)

        imgAdd.setOnClickListener(this)
        imgMinus.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (p0 == imgAdd)
            listenerWeakReference.get()?.addItem(adapterPosition)
        if (p0 == imgMinus)
            listenerWeakReference.get()?.minusItem(adapterPosition)
    }
}