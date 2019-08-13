package com.example.wsupevents.ui.events
import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.wsupevents.models.events.EventCategoriesRes
import com.example.wsupevents.models.events.EventRes
import com.example.wsupevents.models.events.EventsRes
import com.example.wsupevents.models.tickets.IntPaymentRes
import com.example.wsupevents.models.tickets.TicketCart
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.storage.events.EventsRepository
import com.example.wsupevents.storage.tickets.TicketRepository
import com.example.wsupevents.utils.OnRecyclerViewItemClick

class EventViewModel  (application: Application) : AndroidViewModel(application) {
    private var context: Context = application.applicationContext
    private val eventsObservable = MediatorLiveData<Resource<EventsRes>>()
    private val eventObservable = MediatorLiveData<Resource<EventRes>>()
    private val categoriesObservable = MediatorLiveData<Resource<EventCategoriesRes>>()
    private val buyTicketObservable = MediatorLiveData<Resource<IntPaymentRes>>()
    private val cartTicketsObservable = MutableLiveData<TicketCart>()
    var eventsRepository: EventsRepository = EventsRepository(application)
    var ticketRepository: TicketRepository = TicketRepository(application)
    init {
        context = application.applicationContext
        eventObservable.addSource(eventsRepository.eventObservable) { data -> eventObservable.setValue(data) }
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
    fun observeEvent(): LiveData<Resource<EventRes>> {
        return eventObservable
    }
    fun observeCartTickets () : LiveData<TicketCart>{
        return cartTicketsObservable
    }
    fun operateTicket(ticketCart: TicketCart){
        cartTicketsObservable.postValue(ticketCart)
    }
    fun ticketsSelected (): Boolean{
        val ticketCart= cartTicketsObservable.value
        return ticketCart?.ticketTypeModels?.size!! > 0
    }
    fun increament (pos: Int){
        val ticketCart= cartTicketsObservable.value
        ticketCart?.ticketTypeModels!![pos].count++
        cartTicketsObservable.value=ticketCart
    }

    fun decreament (pos: Int){
        val ticketCart= cartTicketsObservable.value
        ticketCart?.ticketTypeModels!![pos].count--
        cartTicketsObservable.value=ticketCart
    }

    fun getEvent(id: Int){
        eventsRepository.viewEvent(id)
    }
    fun fetchEvents(){
        eventsRepository.fetchEvents()
    }
    fun fetchCategories(){
        eventsRepository.fetchCategories()
    }
    fun observeBuyTuckets () : LiveData<Resource<IntPaymentRes>>  {
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
}