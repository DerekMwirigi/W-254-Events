package com.example.wsupevents.ui.main

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.wsupevents.R
import kotlinx.android.synthetic.main.activity_index.*

class IndexActivity : AppCompatActivity() {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    private lateinit var viewModel: IndexViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.view_pager)
        viewModel = ViewModelProviders.of(this).get(IndexViewModel::class.java)

        viewModel.observeTitle().observe(this, Observer {
            kotlin.run {
                tvAppBarTitle.text = it
            }
        })

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Events"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Flights"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Hotels"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = AppTabAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        viewModel.setTitle("Events & Travel")
    }
}