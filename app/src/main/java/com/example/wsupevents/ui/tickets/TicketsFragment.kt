package com.example.wsupevents.ui.tickets

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
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.wsupevents.R
import com.example.wsupevents.models.tickets.HistoryTicketsRes
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.models.xit.Status
import kotlinx.android.synthetic.main.tickets_layout.*
import kotlinx.android.synthetic.main.tickets_layout.avi

class TicketsFragment : Fragment (){
    private lateinit var viewModel: TicketViewModel
    companion object {
        fun newInstance() = TicketsFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.tickets_layout, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TicketViewModel::class.java)
        viewModel.observeTickets().observe(this, Observer { data->
            run {
                setStatus(data)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    rvTickets.adapter = viewModel.getEventsAdapter(data.data!!)
                }
            }
        })
        viewModel.fetchTickets()
        rvTickets.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        rvTickets.itemAnimator = DefaultItemAnimator()!!
    }
    private fun setStatus(data: Resource<HistoryTicketsRes>) {
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