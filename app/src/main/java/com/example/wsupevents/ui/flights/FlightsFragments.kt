package com.example.wsupevents.ui.flights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wsupevents.R

class FlightsFragments  : Fragment() {
    companion object {
        fun newInstance() = FlightsFragments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.flights_layout, container, false)
    }
}