package com.example.wsupevents.ui.tickets

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wsupevents.R
import com.example.wsupevents.models.tickets.TicketTypeModel
import com.example.wsupevents.models.tickets.TicketTypeModelCart
import com.example.wsupevents.utils.OnCartItemClickEvent
import com.google.gson.Gson

class TicketTypeAdapter (private var modelList: List<TicketTypeModel>?, private val itemListener: OnCartItemClickEvent) : RecyclerView.Adapter<TicketTypeViewHolder>() {

    var itemView: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketTypeViewHolder {
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_ticket_type, parent, false)
        return TicketTypeViewHolder(itemView!!, itemListener)
    }
    override fun onBindViewHolder(holder: TicketTypeViewHolder, position: Int) {
        val model = modelList!![position]
        holder.tvLabel.text = model.label
        holder.tvPrice.text = "" +model.price
        holder.tvClossesOn.text = model.clossesOn
        holder.tvCount.text= model.count.toString()
    }

    fun update (modelList: List<TicketTypeModel>?){
        this.modelList = modelList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }
}