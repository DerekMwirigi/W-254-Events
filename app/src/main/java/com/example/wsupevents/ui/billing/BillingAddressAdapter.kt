package com.example.wsupevents.ui.billing

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wsupevents.R
import com.example.wsupevents.models.billing.BillingAddress
import com.example.wsupevents.models.events.Event
import com.example.wsupevents.utils.OnRecyclerViewItemClick
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BillingAddressAdapter(private var modelList: List<BillingAddress?>?, private val recyclerListener: OnRecyclerViewItemClick) : RecyclerView.Adapter<BillingAddressItemViewHolder>() {
    var itemView: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingAddressItemViewHolder {
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_billing_address, parent, false)
        return BillingAddressItemViewHolder(itemView!!, recyclerListener)
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: BillingAddressItemViewHolder, position: Int) {
        val model = modelList!![position]
        holder.tvAccName.text = model?.accName
        holder.tvAccNo.text = model?.accNo
        Glide.with(itemView!!)  //2
            .load(model?.accIcon) //3
            .centerCrop() //4
            .placeholder(R.drawable.ic_launcher_background) //5
            .error(R.drawable.ic_launcher_background) //6
            .fallback(R.drawable.ic_launcher_background) //7
            .into(holder.imgIcon) //8
    }

    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }

    fun refresh(modelList: List<BillingAddress>) {
        this.modelList = modelList
        notifyDataSetChanged()
    }
}
