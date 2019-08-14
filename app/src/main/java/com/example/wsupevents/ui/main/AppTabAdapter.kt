package com.example.wsupevents.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.wsupevents.ui.events.EventsFragment
import com.example.wsupevents.ui.flights.FlightsFragments
import com.example.wsupevents.ui.flights.HotelsFragments
import com.example.wsupevents.ui.profile.ProfileFragment
import com.example.wsupevents.ui.tickets.TicketsFragment

class AppTabAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                EventsFragment()
            }
            1 -> {
                FlightsFragments()
            }
            2 -> {
                HotelsFragments()
            }
            else -> TicketsFragment()
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}