package com.example.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.auth.models.VerifyUId
import com.example.wsupevents.R
import com.example.wsupevents.models.xit.Status
import com.example.wsupevents.ui.auth.AuthViewModel
import com.example.wsupevents.ui.auth.SignUpFragment
import com.example.wsupevents.utils.Xit
import kotlinx.android.synthetic.main.verifyuid_layout.*

class VerifyUIdFragment : Fragment() {
    private lateinit var viewModel: AuthViewModel
    companion object {
        fun newInstance() = VerifyUIdFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.verifyuid_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        viewModel.observeVerifyUId().observe(this, Observer { data->
            run {
                Xit.setState(data.status, data.message, avi, context, activity)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.flContents, VerifyUSecretFragment.newInstance(data.data))
                        .commitNow()
                }
            }
        })
        btNext.setOnClickListener {  viewModel.verifyUID(verifyUId())}
        btSignUp.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.flContents, SignUpFragment.newInstance())
               // ?.addToBackStack("signUp")
                ?.commitNow()
        }
    }

    private fun verifyUId () : VerifyUId {
        return VerifyUId(edtUId.text.toString())
    }
}