package com.example.wsupevents.ui.tickets

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wsupevents.R
import com.example.wsupevents.models.tickets.Ticket
import com.example.wsupevents.utils.OnRecyclerViewItemClick
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TicketAdapter(private var modelList: List<Ticket>?, private val recyclerListener: OnRecyclerViewItemClick) : RecyclerView.Adapter<TicketItemViewHolder>() {
    var itemView: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketItemViewHolder {
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_ticket, parent, false)
        return TicketItemViewHolder(itemView!!, recyclerListener)
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: TicketItemViewHolder, position: Int) {
        val model = modelList!![position]
        holder.label.text = model.label
        holder.tvPaymentStatus.text = model.paymentStatus
        when(holder.tvPaymentStatus.text){
            "Paid"-> {
                holder.tvPaymentStatus.setTextColor(Color.parseColor("#48FF00"))
            }

        }
        holder.description.text = "Tickets " + model.noTickets.toString()
        val eventDate = LocalDate.parse(model.eventDate, DateTimeFormatter.ISO_DATE)
        holder.tvDay.text = eventDate.dayOfMonth.toString()
        holder.tvMonthYear.text = eventDate.month.toString() + " " + eventDate.year
        Glide.with(itemView!!)  //2
            .load(model.image) //3
            .centerCrop() //4
            .placeholder(R.drawable.ic_launcher_background) //5
            .error(R.drawable.ic_launcher_background) //6
            .fallback(R.drawable.ic_launcher_background) //7
            .into(holder.image) //8
    }

    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }

    fun refresh(modelList: List<Ticket>) {
        this.modelList = modelList
        notifyDataSetChanged()
    }
}
