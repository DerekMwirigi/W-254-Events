package com.example.wsupevents.ui.payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wsupevents.R
import com.example.wsupevents.models.billing.BillingAddress
import com.example.wsupevents.ui.billing.BillingAddressAdapter
import com.example.wsupevents.utils.OnRecyclerViewItemClick
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_layout.*

class BottomSheetFragment(billingAddresses: List<BillingAddress?>?) : BottomSheetDialogFragment() {
    private var billingAddresses: List<BillingAddress?>? = billingAddresses
    var billingAddress = MediatorLiveData<BillingAddress>()
    private var fragmentView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.bottom_sheet_layout, container, false)
        return fragmentView
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvPaymentAddresses.adapter = BillingAddressAdapter(billingAddresses, object : OnRecyclerViewItemClick {
            override fun onClickListener(position: Int) {
                billingAddress.postValue(billingAddresses!![position]!!)
               // Toast.makeText(context, "Test" + billingAddress.value?.accNo, Toast.LENGTH_LONG).show()
                //Toast.makeText(context, "Test" + billingAddress.toString(), Toast.LENGTH_LONG).show()
                this@BottomSheetFragment.dismiss()
            }
            override fun onLongClickListener(position: Int) {
            }
        })
        rvPaymentAddresses.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvPaymentAddresses.itemAnimator = DefaultItemAnimator()

    }
}