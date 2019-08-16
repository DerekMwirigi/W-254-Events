package com.example.wsupevents.ui.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.wsupevents.R
import com.example.wsupevents.models.tickets.TicketCart
import com.example.wsupevents.models.xit.Status
import com.example.wsupevents.ui.main.Home
import com.example.wsupevents.utils.Xit
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*

class PaymentActivity : AppCompatActivity() {
    private lateinit var ticketCart: TicketCart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
       // imgBack.setOnClickListener{ onBackPressed() }
        title = ""

        val bundle: Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
                ticketCart = get("ticketCart") as TicketCart
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.llMiddle, PaymentFragment.newInstance(ticketCart))
            .commitNow()
    }
}
