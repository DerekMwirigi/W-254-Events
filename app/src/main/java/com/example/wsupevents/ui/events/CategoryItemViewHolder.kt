package com.example.wsupevents.ui.events

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wsupevents.R
import com.example.wsupevents.utils.OnRecyclerViewItemClick
import java.lang.ref.WeakReference

class CategoryItemViewHolder(itemView: View, listener: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView), View.OnClickListener,
    View.OnLongClickListener {

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(listener)

    var itemVew: View
    var label: TextView
    var image: ImageView
    init {
        this.itemVew = itemView
        label = itemView.findViewById(R.id.tvLabel)
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