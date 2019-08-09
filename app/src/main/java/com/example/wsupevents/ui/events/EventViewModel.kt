package com.example.wsupevents.ui.events

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.wsupevents.R
import com.example.wsupevents.models.events.Event
import com.example.wsupevents.models.events.EventCategoriesRes
import com.example.wsupevents.models.events.EventsRes
import com.example.wsupevents.models.tickets.BuyTicketReq
import com.example.wsupevents.models.tickets.BuyTicketRes
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.models.xit.Status
import com.example.wsupevents.storage.data.PrefrenceManager
import com.example.wsupevents.storage.events.EventsRepository
import com.example.wsupevents.storage.tickets.TicketRepository
import com.example.wsupevents.utils.OnRecyclerViewItemClick
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventViewModel  (application: Application) : AndroidViewModel(application) {
    private var context: Context = application.applicationContext
    private val eventsObservable = MediatorLiveData<Resource<EventsRes>>()
    private val categoriesObservable = MediatorLiveData<Resource<EventCategoriesRes>>()
    private val buyTicketObservable = MediatorLiveData<Resource<BuyTicketRes>>()
    var eventsRepository: EventsRepository = EventsRepository(application)
    var ticketRepository: TicketRepository = TicketRepository(application)
    init {
        context = application.applicationContext
        eventsObservable.addSource(eventsRepository.eventsObservable) { data -> eventsObservable.setValue(data) }
        categoriesObservable.addSource(eventsRepository.categoriesObservable) { data -> categoriesObservable.setValue(data) }
        buyTicketObservable.addSource(ticketRepository.buyTicketObservable) { data -> buyTicketObservable.setValue(data)}
    }
    fun observeEvents(): LiveData<Resource<EventsRes>> {
        return eventsObservable
    }
    fun observeCategories(): LiveData<Resource<EventCategoriesRes>> {
        return categoriesObservable
    }
    fun fetchEvents(){
        eventsRepository.fetchEvents()
    }
    fun fetchCategories(){
        eventsRepository.fetchCategories()
    }
    fun observeBuyTuckets () : LiveData<Resource<BuyTicketRes>>  {
        return buyTicketObservable
    }

    fun getCategoriesAdapter (eventCategoriesRes: EventCategoriesRes) : CategoryAdapter{
        return CategoryAdapter(eventCategoriesRes.data, object : OnRecyclerViewItemClick {
                    override fun onClickListener(position: Int) {
                    }
                    override fun onLongClickListener(position: Int) {
                    }
                })
    }


   @RequiresApi(Build.VERSION_CODES.O)
   fun showEvent (context: Context?, event: Event?){
       var mView: View = inflate(context,  R.layout.popup_event, null)
       val eventDate = LocalDate.parse(event!!.eventDate, DateTimeFormatter.ISO_DATE)
       var imgImage: ImageView = mView.findViewById(R.id.imgImage)
       var tvLabel: TextView = mView.findViewById(R.id.tvLabel)
       var tvDesc: TextView = mView.findViewById(R.id.tvDesc)
       var tvDay: TextView = mView.findViewById(R.id.tvDay)
       var tvMonthYear: TextView = mView.findViewById(R.id.tvMonthYear)
       var edtTickets: TextView = mView.findViewById(R.id.edtTickets)
       tvLabel.text = event!!.label
       tvDesc.text = event!!.description
       tvDay.text = eventDate.dayOfMonth.toString()
       tvMonthYear.text = eventDate.month.toString() + " " + eventDate.year
       Glide.with(mView!!)
           .load(event?.image)
           .centerCrop()
           .placeholder(R.drawable.ic_launcher_background)
           .error(R.drawable.ic_launcher_background)
           .fallback(R.drawable.ic_launcher_background)
           .into(imgImage)
       MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_MaterialComponents)
            .setView(mView)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Ok") { _, _ -> ticketRepository.buyTicket(
                BuyTicketReq(
                    1,
                    PrefrenceManager(context!!).getUserSession()?.mobile,
                    event.id,
                    edtTickets.text.toString().toInt(),
                    (edtTickets.text.toString().toInt() * event.ticketPrice!!)
                )
            )}
            .show()
    }
}