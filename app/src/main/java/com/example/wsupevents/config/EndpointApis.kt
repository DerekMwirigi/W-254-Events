package com.example.wsupevents.config

import com.example.wsupevents.models.events.EventCategoriesRes
import com.example.wsupevents.models.events.EventsRes
import com.example.wsupevents.models.tickets.HistoryTicketsRes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface EndpointApis {
    @GET("event/fetch.php")
    fun fetchEvents(): Call<EventsRes>

    @GET("event/categories.php")
    fun fetchCategories(): Call<EventCategoriesRes>

    @GET("ticket/fetch.php")
    fun fetchTickets(): Call<HistoryTicketsRes>
}