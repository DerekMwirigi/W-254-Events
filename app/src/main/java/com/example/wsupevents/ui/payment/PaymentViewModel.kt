package com.example.wsupevents.ui.payment

import android.app.Application
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.wsupevents.models.billing.BillingAddress
import com.example.wsupevents.models.billing.BillingAddressRes
import com.example.wsupevents.models.tickets.InitPayment
import com.example.wsupevents.models.tickets.IntPaymentRes
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.storage.billing.BillingRepository
import com.example.wsupevents.storage.tickets.TicketRepository

class PaymentViewModel (application: Application) : AndroidViewModel(application){
    private var context: Context = application.applicationContext
    private val initPaymentObservable = MediatorLiveData<Resource<IntPaymentRes>>()
    private val billingAddressesObservable = MediatorLiveData<Resource<BillingAddressRes>>()
    private var ticketRepository: TicketRepository = TicketRepository(application)
    var billingRepository: BillingRepository = BillingRepository(application)
    init {
        context = application.applicationContext
        initPaymentObservable.addSource(ticketRepository.buyTicketObservable) { data -> initPaymentObservable.setValue(data)}
        billingAddressesObservable.addSource(billingRepository.billingAddressesObservable) { data -> billingAddressesObservable.setValue(data)}
    }

    fun observeInitPayment () : LiveData<Resource<IntPaymentRes>> {
        return initPaymentObservable
    }

    fun initPayment (initPayment: InitPayment){
        ticketRepository.initPayment(initPayment)
    }

    fun observeBillingAddresses () : LiveData<Resource<BillingAddressRes>> {
        return billingAddressesObservable
    }

    fun getBillingAddresses () {
        billingRepository.getBillingAddresses()
    }
}