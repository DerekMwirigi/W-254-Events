package com.example.wsupevents.ui.billing

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.wsupevents.R
import com.example.wsupevents.models.billing.BillingAddress
import com.example.wsupevents.models.xit.Status
import com.example.wsupevents.ui.payment.PaymentViewModel
import com.example.wsupevents.utils.Xit
import kotlinx.android.synthetic.main.card_billing_address.*
import kotlinx.android.synthetic.main.new_mpesa_number_fragment.*

class NewMpesaNumberFragment : Fragment() {
    private lateinit var viewModel: BillingViewModel
    companion object {
        fun newInstance() = NewMpesaNumberFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.new_mpesa_number_fragment, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imgBack.setOnClickListener{ activity?.onBackPressed() }
        viewModel = ViewModelProviders.of(this).get(BillingViewModel::class.java)
        viewModel.observeBillingAddresses().observe(this, Observer {data->
            run {
                Xit.setState(data.status, data.message, avi, context, activity)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    activity?.onBackPressed()
                }
            }
        })
        btSave.setOnClickListener{
            viewModel.saveBillingAddress(getData())
        }
    }

    private fun getData () : BillingAddress{
        return BillingAddress(1,
            edtAccName.text.toString(),
            edtNumber.text.toString(),
            "https://mpasho254.files.wordpress.com/2018/11/mpesa.png")
    }

}