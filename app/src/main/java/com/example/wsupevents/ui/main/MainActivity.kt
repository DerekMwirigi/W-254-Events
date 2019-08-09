package com.example.wsupevents.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wsupevents.R
import com.example.wsupevents.ui.events.EventsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.llMiddle, EventsFragment.newInstance())
                .commitNow()
        }
    }
    override fun onResume() {
        super.onResume()
    }
}
