package com.example.wsupevents.ui.events

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.bumptech.glide.Glide
import com.example.wsupevents.R
import com.example.wsupevents.models.events.Event
import com.example.wsupevents.models.tickets.TicketCart
import com.example.wsupevents.models.tickets.TicketTypeModel
import com.example.wsupevents.models.tickets.TicketTypeModelCart
import com.example.wsupevents.models.xit.Status
import com.example.wsupevents.ui.payment.PaymentActivity
import com.example.wsupevents.ui.tickets.TicketTypeAdapter
import com.example.wsupevents.ui.tickets.TicketViewModel
import com.example.wsupevents.utils.OnCartItemClickEvent
import com.example.wsupevents.utils.Xit
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.content_event.*
import kotlinx.android.synthetic.main.content_event.avi

class EventActivity : AppCompatActivity() {
    private lateinit var viewModel: EventViewModel
    private lateinit var ticketViewModel: TicketViewModel
    private lateinit var activity: Activity
    private lateinit var ticketTypeAdapter : TicketTypeAdapter
    private lateinit var event: Event
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        activity = this
        ticketViewModel = ViewModelProviders.of(this).get(TicketViewModel::class.java)
        viewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        viewModel.observeEvent().observe(this, Observer { data->
            run {
                Xit.setState(data.status, data.message, avi, this, this)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    setUpUI(data.data.data)
                }
            }
        })
        ticketViewModel.observeCartTickets().observe(this, Observer { it->
            run {
                ticketTypeAdapter.update(it.ticketTypeModels)
                btProceed.isEnabled = ticketViewModel.ticketsSelected()
            }
        })
        val bundle: Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
                event = get("eventModel") as Event
                viewModel.getEvent(event?.id!!)
                title=event.label
            }
        }
        rvTicketTypes.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        rvTicketTypes.itemAnimator = DefaultItemAnimator()

        btProceed.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("ticketCart", ticketViewModel.observeCartTickets().value)
            startActivity(intent)
        }
    }

    private fun setUpUI(event: Event?){
        tvLabel.text = event?.label
        tvPlanner.text = "by " + event?.plannerName
        tvDate.text = event?.eventDate
        tvLocation.text = event?.locationName
        tvDesc.text = event?.description
        Glide.with(this)
           .load(event?.image)
           .centerCrop()
           .placeholder(R.drawable.ic_launcher_background)
           .error(R.drawable.ic_launcher_background)
           .fallback(R.drawable.ic_launcher_background)
           .into(imgImage)
        ticketViewModel.operateTicket(TicketCart(event?.label, event?.id, prepareTicketTypes(event)))
        ticketTypeAdapter = TicketTypeAdapter(emptyList(), object : OnCartItemClickEvent {
            override fun deleteItem(pos: Int) {
            }
            override fun addItem(pos: Int) {
                ticketViewModel.ticketAdd(pos)
            }
            override fun minusItem(pos: Int) {
                ticketViewModel.ticketMinus(pos)
            }
        })
        rvTicketTypes.adapter = ticketTypeAdapter
    }
    private fun prepareTicketTypes(event: Event?) : List<TicketTypeModel>{
        val ticketTypeModels: ArrayList<TicketTypeModel> = ArrayList()
        event?.ticketTypeModels?.forEach {
            ticketTypeModels.add(TicketTypeModel(count = 1, clossesOn = it.clossesOn, price = it.price, id = it.id, label = it.label, maxBuy = it.maxBuy))
        }
        return  ticketTypeModels
    }
}
