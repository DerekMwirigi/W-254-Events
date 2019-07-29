package com.example.wsupevents.ui.events

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.wsupevents.models.events.EventCategoriesRes
import com.example.wsupevents.models.events.EventsRes
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.storage.events.EventsRepository
import com.example.wsupevents.utils.OnRecyclerViewItemClick

class EventViewModel  (application: Application) : AndroidViewModel(application) {
    private val eventsObservable = MediatorLiveData<Resource<EventsRes>>()
    private val categoriesObservable = MediatorLiveData<Resource<EventCategoriesRes>>()
    var eventsRepository: EventsRepository = EventsRepository(application)

    init {
        eventsObservable.addSource(eventsRepository.eventsObservable) { data -> eventsObservable.setValue(data) }
        categoriesObservable.addSource(eventsRepository.categoriesObservable) { data -> categoriesObservable.setValue(data) }
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

    fun getEventsAdapter (eventsRes: EventsRes) : EventAdapter{
        return EventAdapter(eventsRes.data, object : OnRecyclerViewItemClick {
                    override fun onClickListener(position: Int) {
                    }
                    override fun onLongClickListener(position: Int) {
                    }
                })
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