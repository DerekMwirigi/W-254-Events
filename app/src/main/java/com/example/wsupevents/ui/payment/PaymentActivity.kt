package com.example.wsupevents.ui.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wsupevents.R
import com.example.wsupevents.utils.Xit

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        Xit.makeFullScreen(this)
    }
}
