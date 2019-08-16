package com.example.wsupevents.ui.events

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wsupevents.R
import com.example.wsupevents.models.events.Event
import com.example.wsupevents.utils.OnRecyclerViewItemClick
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventAdapter(private var modelList: List<Event>?, private val recyclerListener: OnRecyclerViewItemClick) : RecyclerView.Adapter<EventItemViewHolder>() {
    var itemView: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemViewHolder {
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_featured, parent, false)
        return EventItemViewHolder(itemView!!, recyclerListener)
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: EventItemViewHolder, position: Int) {
        val model = modelList!![position]
        holder.label.text = model.label
        holder.description.text = model.plannerName
        val eventDate = LocalDate.parse(model.eventDate, DateTimeFormatter.ISO_DATE)
        holder.tvDay.text = eventDate.dayOfMonth.toString()
        holder.tvMonthYear.text = eventDate.month.toString()
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

    fun refresh(modelList: List<Event>) {
        this.modelList = modelList
        notifyDataSetChanged()
    }
}
