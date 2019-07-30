package com.example.wsupevents.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wsupevents.R
import com.example.wsupevents.models.auth.User
import com.example.wsupevents.models.xit.Status
import com.example.wsupevents.utils.CircleTransform
import com.example.wsupevents.utils.Xit
import kotlinx.android.synthetic.main.profile_layout.*

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    companion object {
        fun newInstance() = ProfileFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.profile_layout, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.observeProfile().observe(this, Observer { data->
            run {
                setUpUi(data)
            }
        })
        viewModel.getProfile()
    }

    private fun setUpUi(user: User?){
        Glide.with(context!!)
            .load(user?.image)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .fallback(R.drawable.ic_launcher_background)
            .into(imgImage)
        edtFirstNameC.helperText = user?.firstName
    }
}