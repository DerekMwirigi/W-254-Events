package com.example.wsupevents.ui.events

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.wsupevents.R
import com.example.wsupevents.models.events.EventCategory
import com.example.wsupevents.utils.CircleTransform
import com.example.wsupevents.utils.OnRecyclerViewItemClick

class CategoryAdapter(private var modelList: List<EventCategory>?, private val recyclerListener: OnRecyclerViewItemClick) : RecyclerView.Adapter<CategoryItemViewHolder>() {
    var itemView: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_category, parent, false)
        return CategoryItemViewHolder(itemView!!, recyclerListener)
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val model = modelList!![position]
        holder.label.text = model.category
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

    fun refresh(modelList: List<EventCategory>) {
        this.modelList = modelList
        notifyDataSetChanged()
    }
}
