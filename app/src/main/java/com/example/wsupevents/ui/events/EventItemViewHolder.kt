package com.example.wsupevents.ui.events

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wsupevents.R
import com.example.wsupevents.utils.OnRecyclerViewItemClick
import java.lang.ref.WeakReference

class EventItemViewHolder(itemView: View, listener: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView), View.OnClickListener,
    View.OnLongClickListener {

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(listener)

    var itemVew: View
    var label: TextView
    var description: TextView
    var image: ImageView
    var tvMonthYear: TextView
    var tvDay: TextView
    init {
        this.itemVew = itemView
        label = itemView.findViewById(R.id.tvLabel)
        description = itemView.findViewById(R.id.tvDesc)
        tvMonthYear = itemView.findViewById(R.id.tvMonthYear)
        tvDay = itemView.findViewById(R.id.tvDay)
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