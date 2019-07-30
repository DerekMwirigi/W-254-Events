package com.example.auth.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.auth.models.VerifyUSecret
import com.example.wsupevents.R
import com.example.wsupevents.models.auth.VerifyUIdRes
import com.example.wsupevents.models.xit.Status
import com.example.wsupevents.storage.data.PrefrenceManager
import com.example.wsupevents.ui.main.Home
import com.example.wsupevents.ui.auth.AuthViewModel
import com.example.wsupevents.utils.Xit
import kotlinx.android.synthetic.main.verifyusecret_layout.*

class VerifyUSecretFragment : Fragment() {
    private lateinit var viewModel: AuthViewModel
    var verifyUIdRes: VerifyUIdRes?=null
    companion object {
        fun newInstance(verifyUIdRes: VerifyUIdRes?) = VerifyUSecretFragment().apply {
            arguments = Bundle().apply {
                putSerializable("verifyUIdRes", verifyUIdRes)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.verifyusecret_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        verifyUIdRes=(arguments?.getSerializable("verifyUIdRes")) as VerifyUIdRes
        tvWelcome.text = verifyUIdRes?.message
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        viewModel.observeVerifyUSecret().observe(this, Observer { data->
            run {
                Xit.setState(data.status, data.message, avi, context, activity)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    PrefrenceManager(context!!).setLoginStatus(1)
                    startActivity(Intent(activity!!, Home::class.java))
                    activity!!.finish()
                }
            }
        })
        btNext.setOnClickListener {
            viewModel.verifyUSecret(VerifyUSecret(edtUPassword.text.toString(), verifyUIdRes?.data?.mobile))
        }
    }
}