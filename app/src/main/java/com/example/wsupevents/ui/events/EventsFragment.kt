package com.example.wsupevents.ui.events

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.models.xit.Status
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
                setStatus(data)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    rvEvents.adapter = viewModel.getEventsAdapter(data.data!!)
                }
            }
        })
        viewModel.fetchEvents()
        rvEvents.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvEvents.itemAnimator = DefaultItemAnimator()!!

        viewModel.observeCategories().observe(this, Observer { data->
            run {
                //setStatus(data)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    rvCategories.adapter = viewModel.getCategoriesAdapter(data.data!!)
                }
            }
        })
        viewModel.fetchCategories()
        rvCategories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.itemAnimator = DefaultItemAnimator()
    }

    private fun setStatus(data: Resource<EventsRes>) {
        val status: Status =data.status
        if(status== Status.LOADING){
            avi.visibility = View.VISIBLE
            activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }else if(status== Status.ERROR||status== Status.SUCCESS){
            avi.visibility = View.GONE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
        if(status== Status.ERROR){
            if(data.message!=null) {
                view?.let { Toast.makeText(context,data.message, Toast.LENGTH_LONG).show() }
            }
        }
    }
}