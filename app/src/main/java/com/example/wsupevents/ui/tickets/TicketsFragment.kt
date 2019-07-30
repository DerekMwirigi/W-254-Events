package com.example.wsupevents.ui.tickets

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.wsupevents.R
import com.example.wsupevents.models.xit.Status
import com.example.wsupevents.utils.Xit
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
                Xit.setState(data.status, data.message, avi, context, activity)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    rvTickets.adapter = viewModel.getTicketsAdapter(data.data!!)
                }
            }
        })
        viewModel.fetchTickets()
        rvTickets.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        rvTickets.itemAnimator = DefaultItemAnimator()!!
    }
}