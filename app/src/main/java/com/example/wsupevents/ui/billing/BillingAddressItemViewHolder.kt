package com.example.wsupevents.ui.billing
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wsupevents.R
import com.example.wsupevents.utils.OnRecyclerViewItemClick
import java.lang.ref.WeakReference

class BillingAddressItemViewHolder(itemView: View, listener: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView), View.OnClickListener,
    View.OnLongClickListener {

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(listener)

    var itemVew: View
    var tvAccName: TextView
    var tvAccNo: TextView
    var imgIcon: ImageView
    init {
        this.itemVew = itemView
        tvAccName = itemView.findViewById(R.id.tvAccName)
        tvAccNo = itemView.findViewById(R.id.tvAccNo)
        imgIcon = itemView.findViewById(R.id.imgIcon)

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