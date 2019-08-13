package com.example.wsupevents.network


import com.example.auth.models.VerifyUId
import com.example.auth.models.VerifyUSecret
import com.example.wsupevents.models.auth.SignUp
import com.example.wsupevents.models.auth.VerifyUIdRes
import com.example.wsupevents.models.auth.VerifyUSecretRes
import com.example.wsupevents.models.events.EventCategoriesRes
import com.example.wsupevents.models.events.EventRes
import com.example.wsupevents.models.events.EventsRes
import com.example.wsupevents.models.tickets.BuyTicketReq
import com.example.wsupevents.models.tickets.IntPaymentRes
import com.example.wsupevents.models.tickets.HistoryTicketsRes
import com.example.wsupevents.models.tickets.InitPayment
import retrofit2.Call
import retrofit2.http.*

interface EndpointApis {

    @POST("auth/signup.php")
    fun signUp (@Body signUp: SignUp?) : Call<VerifyUSecretRes>

    @POST("auth/verifyuid.php")
    fun verifyUID (@Body verifyUId: VerifyUId?) : Call<VerifyUIdRes>

    @POST("auth/verifyusecret.php")
    fun verifyUSecret (@Body verifyUSecret: VerifyUSecret?) : Call<VerifyUSecretRes>

    @FormUrlEncoded
    @POST("account/updatefirebasetoken.php")
    fun updateFirebaseToken (@Field("firebaseToken") firebaseToken: String) : Call<VerifyUSecretRes>

    @GET("event/view.php")
    fun viewEvent (@Query("id") id: Int): Call<EventRes>

    @GET("event/fetch.php")
    fun fetchEvents(): Call<EventsRes>

    @GET("event/categories.php")
    fun fetchCategories(): Call<EventCategoriesRes>

    @GET("ticket/fetch.php")
    fun fetchTickets(): Call<HistoryTicketsRes>

    @POST("ticket/create.php")
    fun createTicket(@Body buyTicketReq: BuyTicketReq?): Call<IntPaymentRes>

    @POST("ticket/init-payment.php")
    fun initPayment(@Body initPayment: InitPayment?): Call<IntPaymentRes>
}