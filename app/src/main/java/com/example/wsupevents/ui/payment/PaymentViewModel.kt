package com.example.wsupevents.ui.payment

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.wsupevents.models.tickets.InitPayment
import com.example.wsupevents.models.tickets.IntPaymentRes
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.storage.tickets.TicketRepository

class PaymentViewModel (application: Application) : AndroidViewModel(application){
    private var context: Context = application.applicationContext
    private val initPaymentObservable = MediatorLiveData<Resource<IntPaymentRes>>()
    var ticketRepository: TicketRepository = TicketRepository(application)
    init {
        context = application.applicationContext
        initPaymentObservable.addSource(ticketRepository.buyTicketObservable) { data -> initPaymentObservable.setValue(data)}
    }

    fun observeInitPayment () : LiveData<Resource<IntPaymentRes>> {
        return initPaymentObservable
    }

    fun initPayment (initPayment: InitPayment){
        ticketRepository.initPayment(initPayment)
    }
}