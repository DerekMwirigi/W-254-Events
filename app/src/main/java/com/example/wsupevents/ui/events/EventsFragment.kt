package com.example.wsupevents.ui.events

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wsupevents.R
import com.example.wsupevents.models.events.EventCategoriesRes
import com.example.wsupevents.models.events.EventsRes
import com.example.wsupevents.models.tickets.BuyTicketRes
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.models.xit.Status
import com.example.wsupevents.utils.OnRecyclerViewItemClick
import com.example.wsupevents.utils.Xit
import kotlinx.android.synthetic.main.events_layout.*

class EventsFragment : Fragment() {
    private lateinit var viewModel: EventViewModel
    companion object {
        fun newInstance() = EventsFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.events_layout, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        viewModel.observeEvents().observe(this, Observer { data->
            run {
                Xit.setState(data.status, data.message, avi, context, activity)
                if(data.status== Status.SUCCESS&&data.data!=null){
                   rvEvents.adapter = EventAdapter(data.data.data!!, object : OnRecyclerViewItemClick {
                       override fun onClickListener(position: Int) {
                           viewModel.showEvent(context, data.data.data!![position])
                       }
                       override fun onLongClickListener(position: Int) {
                       }
                   })
                }
            }
        })
        viewModel.fetchEvents()
        rvEvents.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        rvEvents.itemAnimator = DefaultItemAnimator()
        viewModel.observeCategories().observe(this, Observer { data->
            run {
                Xit.setState(data.status, data.message, avi2, context, activity)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    rvCategories.adapter = viewModel.getCategoriesAdapter(data.data!!)
                }
            }
        })
        viewModel.fetchCategories()
        rvCategories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.itemAnimator = DefaultItemAnimator()

        viewModel.observeBuyTuckets().observe(this, Observer { data->
            run {
                Xit.setState(data.status, data.message, avi, context, activity)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    Toast.makeText(context, data.data.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        //viewModel.showEvent(context, null)
    }
}