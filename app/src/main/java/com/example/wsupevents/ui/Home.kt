package com.example.wsupevents.ui

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.wsupevents.R
import com.example.wsupevents.services.FirebaseMessaging
import com.example.wsupevents.ui.events.EventsFragment
import com.example.wsupevents.ui.profile.ProfileFragment
import com.example.wsupevents.ui.tickets.TicketsFragment
import com.example.wsupevents.utils.Xit

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
                supportFragmentManager.beginTransaction()
                    .replace(R.id.llMiddle, ProfileFragment.newInstance())
                    .commitNow()
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
        startService(Intent(this, FirebaseMessaging::class.java))
        Xit.makeFullScreen(this)
    }
    override fun onResume() {
        super.onResume()
    }
}
