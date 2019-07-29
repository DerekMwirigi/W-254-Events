package com.example.wsupevents.ui.tickets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.wsupevents.models.tickets.HistoryTicketsRes
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.storage.tickets.TicketRepository
import com.example.wsupevents.utils.OnRecyclerViewItemClick

class TicketViewModel (application: Application) : AndroidViewModel(application) {
    private val ticketsObservable = MediatorLiveData<Resource<HistoryTicketsRes>>()

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

    fun getEventsAdapter (historyTicketsRes: HistoryTicketsRes) : TicketAdapter {
        return TicketAdapter(historyTicketsRes.data, object : OnRecyclerViewItemClick {
            override fun onClickListener(position: Int) {
            }
            override fun onLongClickListener(position: Int) {
            }
        })
    }
}