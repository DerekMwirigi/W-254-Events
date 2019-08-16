package com.example.wsupevents.ui.billing

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.wsupevents.models.billing.BillingAddress
import com.example.wsupevents.models.billing.BillingAddressRes
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.storage.billing.BillingRepository

class BillingViewModel (application: Application) : AndroidViewModel(application){
    private var context: Context = application.applicationContext
    var billingRepository: BillingRepository = BillingRepository(application)
    private val billingAddressesObservable = MediatorLiveData<Resource<BillingAddressRes>>()
    init {
        context = application.applicationContext
        billingAddressesObservable.addSource(billingRepository.billingAddressesObservable) { data -> billingAddressesObservable.setValue(data)}
    }

    fun saveBillingAddress (billingAddress: BillingAddress){
        billingRepository.saveBillingAddress(billingAddress)
    }
    fun observeBillingAddresses () : LiveData<Resource<BillingAddressRes>> {
        return billingAddressesObservable
    }
}