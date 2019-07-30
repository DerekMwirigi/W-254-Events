package com.example.wsupevents.ui.tickets

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.afollestad.materialdialogs.MaterialDialog
import com.example.wsupevents.models.tickets.HistoryTicketsRes
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.storage.tickets.TicketRepository
import com.example.wsupevents.utils.OnRecyclerViewItemClick

class TicketViewModel (application: Application) : AndroidViewModel(application) {
    private val ticketsObservable = MediatorLiveData<Resource<HistoryTicketsRes>>()
    private val context: Context = application.applicationContext
    private var ticketRepository: TicketRepository = TicketRepository(application)
    init {
        ticketsObservable.addSource(ticketRepository.ticketsObservable) { data -> ticketsObservable.setValue(data) }
    }

    fun observeTickets(): LiveData<Resource<HistoryTicketsRes>> {
        return ticketsObservable
    }

    fun fetchTickets (){
        ticketRepository.fetchTickets()
    }

    fun getTicketsAdapter (historyTicketsRes: HistoryTicketsRes) : TicketAdapter {
        return TicketAdapter(historyTicketsRes.data, object : OnRecyclerViewItemClick {
            override fun onClickListener(position: Int) {
            }
            override fun onLongClickListener(position: Int) {
                Toast.makeText(context, position.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}