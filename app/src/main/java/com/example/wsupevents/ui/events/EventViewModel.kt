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
    private val featuredObservable = MediatorLiveData<Resource<EventsRes>>()
    private val favouriteObservable = MediatorLiveData<Resource<EventsRes>>()
    private val moreObservable = MediatorLiveData<Resource<EventsRes>>()
    private val eventObservable = MediatorLiveData<Resource<EventRes>>()
    private val categoriesObservable = MediatorLiveData<Resource<EventCategoriesRes>>()
    var eventsRepository: EventsRepository = EventsRepository(application)
    init {
        context = application.applicationContext
        eventObservable.addSource(eventsRepository.eventObservable) { data -> eventObservable.setValue(data) }
        featuredObservable.addSource(eventsRepository.featuredObservable) { data -> featuredObservable.setValue(data) }
        favouriteObservable.addSource(eventsRepository.favouriteObservable) { data -> favouriteObservable.setValue(data) }
        moreObservable.addSource(eventsRepository.moreObservable) { data -> moreObservable.setValue(data) }
        categoriesObservable.addSource(eventsRepository.categoriesObservable) { data -> categoriesObservable.setValue(data) }
    }
    fun observeEvents(): LiveData<Resource<EventsRes>> {
        return featuredObservable
    }
    fun observeCategories(): LiveData<Resource<EventCategoriesRes>> {
        return categoriesObservable
    }
    fun observeEvent(): LiveData<Resource<EventRes>> {
        return eventObservable
    }

    fun getEvent(id: Int){
        eventsRepository.viewEvent(id)
    }
    fun fetchEvents(){
        eventsRepository.featuredEvents()
    }
    fun fetchCategories(){
        eventsRepository.fetchCategories()
    }

    fun getCategoriesAdapter (eventCategoriesRes: EventCategoriesRes) : CategoryAdapter{
        return CategoryAdapter(eventCategoriesRes.data, object : OnRecyclerViewItemClick {
                    override fun onClickListener(position: Int) {
                    }
                    override fun onLongClickListener(position: Int) {
                    }
                })
    }

    fun favouriteEvents(){
        eventsRepository.favouriteEvents()
    }

    fun observeFavouriteEvents(): LiveData<Resource<EventsRes>> {
        return favouriteObservable
    }

    fun moreEvents(){
        eventsRepository.moreEvents()
    }

    fun observeMoreEvents(): LiveData<Resource<EventsRes>> {
        return moreObservable
    }
}