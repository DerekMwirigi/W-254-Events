package com.example.wsupevents.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.wsupevents.R
import com.example.wsupevents.ui.events.EventsFragment
import com.example.wsupevents.ui.tickets.TicketsFragment

class Home : AppCompatActivity() {


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_events -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.llMiddle, EventsFragment.newInstance())
                    .commitNow()
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_tickets -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.llMiddle, TicketsFragment.newInstance())
                    .commitNow()
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.llMiddle, EventsFragment.newInstance())
                .commitNow()
        }
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
    override fun onResume() {
        super.onResume()
    }
}
