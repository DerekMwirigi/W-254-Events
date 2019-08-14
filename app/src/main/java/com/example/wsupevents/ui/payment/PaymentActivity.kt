package com.example.wsupevents.ui.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.wsupevents.R
import com.example.wsupevents.models.tickets.InitPayment
import com.example.wsupevents.models.tickets.TicketCart
import com.example.wsupevents.models.xit.Status
import com.example.wsupevents.storage.data.PrefrenceManager
import com.example.wsupevents.ui.main.Home
import com.example.wsupevents.utils.Xit
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {
    private lateinit var  ticketCart: TicketCart
    private lateinit var viewModel: PaymentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
//        Xit.makeFullScreen(this)
        title = ""
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
        viewModel.observeInitPayment().observe(this, androidx.lifecycle.Observer {data ->
            run {
                Xit.setState(data.status, data.message, avi, this, this)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    val intent = Intent(applicationContext, Home::class.java)
                    startActivity(intent)
                    finish()
                }
                Toast.makeText(this@PaymentActivity, data.data?.message, Toast.LENGTH_LONG).show()
            }
        })
        val bundle: Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
                ticketCart = get("ticketCart") as TicketCart
            }
        }
        tvLabel.text = ticketCart.label
        tvAmount.text = "KES " + getTotal()
        btContinue.setOnClickListener{ viewModel.initPayment(InitPayment(ticketCart,
            1,
            PrefrenceManager(this).getUserSession()?.mobile,
            getTotal()))  }
        cgPayments.setOnCheckedChangeListener { group, checkedId ->
            Toast.makeText(this, ""  +checkedId, Toast.LENGTH_LONG).show()
        }
        cpMpesa.setOnClickListener { selectPayment(cpMpesa) }
        cpCard.setOnClickListener { selectPayment(cpCard) }
    }

    private fun selectPayment(view: Chip){
        cpCard.chipIcon = null
        cpMpesa.chipIcon = null
        view.chipIcon = ContextCompat.getDrawable(this, R.drawable.ic_check_circle_black_24dp)
        if(view == cpMpesa){

        }else{

        }
    }
    private fun getTotal () : Double {
        var total = 0.00
        ticketCart.ticketTypeModels?.forEach {
            total += (it?.price!! * it?.count!!)
        }
        return total
    }
}
