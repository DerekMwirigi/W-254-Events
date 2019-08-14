package com.example.wsupevents.ui.flights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.wsupevents.R
import com.example.wsupevents.ui.main.IndexViewModel

class HotelsFragments  : Fragment() {
    private lateinit var indexViewModel: IndexViewModel
    companion object {
        fun newInstance() = HotelsFragments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.hotels_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        indexViewModel = ViewModelProviders.of(this).get(IndexViewModel::class.java)
        indexViewModel.setTitle("Flights")
    }
}