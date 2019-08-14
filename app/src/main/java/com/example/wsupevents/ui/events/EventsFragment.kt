package com.example.wsupevents.ui.events

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wsupevents.R
import com.example.wsupevents.models.xit.Status
import com.example.wsupevents.ui.main.IndexViewModel
import com.example.wsupevents.utils.OnRecyclerViewItemClick
import com.example.wsupevents.utils.Xit
import kotlinx.android.synthetic.main.events_layout.*

class EventsFragment : Fragment() {
    private lateinit var viewModel: EventViewModel
    private lateinit var indexViewModel: IndexViewModel
    companion object {
        fun newInstance() = EventsFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.events_layout, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        indexViewModel = ViewModelProviders.of(this).get(IndexViewModel::class.java)
        indexViewModel.setTitle("Events")
        viewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)

//        viewModel.observeEvents().observe(this, Observer { data->
//            run {
//                Xit.setState(data.status, data.message, avi, context, activity)
//                if(data.status== Status.SUCCESS&&data.data!=null){
//                   rvEvents.adapter = EventAdapter(data.data.data!!, object : OnRecyclerViewItemClick {
//                       override fun onClickListener(position: Int) {
//                           val intent = Intent(context, EventActivity::class.java)
//                           intent.putExtra("eventModel", data.data.data!![position])
//                           startActivity(intent)
//                       }
//                       override fun onLongClickListener(position: Int) {
//                       }
//                   })
//                }
//            }
//        })
//        viewModel.fetchEvents()
//        rvEvents.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        rvEvents.itemAnimator = DefaultItemAnimator()

        viewModel.observeCategories().observe(this, Observer { data->
            run {
                Xit.setState(data.status, data.message, avi2, context, activity)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    rvCategories.adapter = viewModel.getCategoriesAdapter(data.data!!)
                }
            }
        })
        viewModel.fetchCategories()
        rvCategories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.itemAnimator = DefaultItemAnimator()

//        viewModel.observeFavouriteEvents().observe(this, Observer { data->
//            run {
//                Xit.setState(data.status, data.message, avi3, context, activity)
//                if(data.status== Status.SUCCESS&&data.data!=null){
//                    rvFavourites.adapter = FavouriteEventAdapter(data.data.data!!, object : OnRecyclerViewItemClick {
//                        override fun onClickListener(position: Int) {
//                            val intent = Intent(context, EventActivity::class.java)
//                            intent.putExtra("eventModel", data.data.data!![position])
//                            startActivity(intent)
//                        }
//                        override fun onLongClickListener(position: Int) {
//                        }
//                    })
//                }
//            }
//        })
//        viewModel.favouriteEvents()
//        rvFavourites.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        rvFavourites.itemAnimator = DefaultItemAnimator()

        viewModel.observeMoreEvents().observe(this, Observer { data->
            run {
                Xit.setState(data.status, data.message, avi4, context, activity)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    rvMore.adapter = MoreEventAdapter(data.data.data!!, object : OnRecyclerViewItemClick {
                        override fun onClickListener(position: Int) {
                            val intent = Intent(context, EventActivity::class.java)
                            intent.putExtra("eventModel", data.data.data!![position])
                            startActivity(intent)
                        }
                        override fun onLongClickListener(position: Int) {
                        }
                    })
                }
            }
        })
        viewModel.moreEvents()
        rvMore.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvMore.itemAnimator = DefaultItemAnimator()
    }
}