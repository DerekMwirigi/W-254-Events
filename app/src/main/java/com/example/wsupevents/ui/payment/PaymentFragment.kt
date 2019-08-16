package com.example.wsupevents.ui.payment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.wsupevents.R
import com.example.wsupevents.models.billing.BillingAddress
import com.example.wsupevents.models.tickets.InitPayment
import com.example.wsupevents.models.tickets.TicketCart
import com.example.wsupevents.models.xit.Status
import com.example.wsupevents.ui.billing.NewMpesaNumberFragment
import com.example.wsupevents.ui.main.Home
import com.example.wsupevents.ui.main.IndexActivity
import com.example.wsupevents.utils.Xit
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.payment_fragment.*

class PaymentFragment (var ticketCart: TicketCart) : Fragment() {
    private lateinit var viewModel: PaymentViewModel
    private var billingAddresses: List<BillingAddress?>? = null
    private val billingAddressObservable = MediatorLiveData<BillingAddress>()
    private var billingAddress: BillingAddress? = null
    companion object {
        fun newInstance(ticketCart: TicketCart) = PaymentFragment(ticketCart)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.payment_fragment, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
        viewModel.observeInitPayment().observe(this, androidx.lifecycle.Observer {data ->
            run {
                Xit.setState(data.status, data.message, avi, context, this.activity)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    val intent = Intent(context, IndexActivity::class.java)
                    startActivity(intent)
                    this.activity?.finish()
                }
            }
        })

        tvLabel.text = ticketCart.label
        tvAmount.text = "KES " + getTotal()
        btContinue.setOnClickListener{
            if(billingAddress!=null){
                viewModel.initPayment(
                    InitPayment(ticketCart,
                        1,
                        billingAddress?.accNo,
                        getTotal())
                )
            }
        }

        viewModel.observeBillingAddresses().observe(this, Observer { data->
            if(data.status== Status.SUCCESS&&data.data!=null) {
                billingAddresses = data.data.data!!
            }
        })
        viewModel.getBillingAddresses()

        billingAddressObservable.observe(this, Observer { data->
            if(data!=null){
                billingAddress = data
                tvPaymentOption.text = billingAddress?.accName + " (" + billingAddress?.accNo + ")"
            }
        })
        imgBack.setOnClickListener{ activity?.onBackPressed() }

        cpMpesa.setOnClickListener { selectPayment(cpMpesa) }
        cpCard.setOnClickListener { selectPayment(cpCard) }

        tvPaymentOption.setOnClickListener {
            showBottomSheetDialogFragment()
        }

        tvAddPayment.setOnClickListener {
            if ((it as TextView).text == "+Add new M-Pesa Number"){
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.llMiddle, NewMpesaNumberFragment.newInstance())
                    ?.addToBackStack("MpesaNumber")
                    ?.commit()
            }else{

            }
        }
    }

    private fun selectPayment(view: Chip){
        cpCard.chipIcon = null
        cpMpesa.chipIcon = null
        view.chipIcon = ContextCompat.getDrawable(context!!, R.drawable.ic_check_circle_black_24dp)
        if(view == cpMpesa){
            tvAddPayment.text = "+Add new M-Pesa Number"
        }else{
            tvAddPayment.text = "+Add new Card"
        }
    }

    private fun getTotal () : Double {
        var total = 0.00
        ticketCart.ticketTypeModels?.forEach {
            total += (it?.price!! * it?.count!!)
        }
        return total
    }

    private fun showBottomSheetDialogFragment() {
        if(billingAddresses!=null){
            val bottomSheetFragment = BottomSheetFragment(billingAddresses)
            bottomSheetFragment.show(activity!!.supportFragmentManager, bottomSheetFragment.tag)
            billingAddressObservable.addSource(bottomSheetFragment.billingAddress){ data -> billingAddressObservable.setValue(data)}
        }
    }
}