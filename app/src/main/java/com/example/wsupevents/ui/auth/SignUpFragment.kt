package com.example.wsupevents.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.auth.models.VerifyUId
import com.example.auth.ui.VerifyUIdFragment
import com.example.auth.ui.VerifyUSecretFragment
import com.example.wsupevents.R
import com.example.wsupevents.models.auth.SignUp
import com.example.wsupevents.models.xit.Status
import com.example.wsupevents.storage.data.PrefrenceManager
import com.example.wsupevents.ui.main.Home
import com.example.wsupevents.utils.Xit
import kotlinx.android.synthetic.main.profile_layout.*
import kotlinx.android.synthetic.main.signup_layout.*
import kotlinx.android.synthetic.main.signup_layout.avi

class SignUpFragment : Fragment() {
    private lateinit var viewModel: AuthViewModel
    companion object {
        fun newInstance() = SignUpFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.signup_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        viewModel.observeVerifyUSecret().observe(this, Observer { data->
            run {
                Xit.setState(data.status, data.message, avi, context, activity)
                PrefrenceManager(context!!).setLoginStatus(1)
                startActivity(Intent(activity!!, Home::class.java))
                activity!!.finish()
            }
        })
        btSignUp.setOnClickListener {  viewModel.signUp(verifyUId())}
        btSignIn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.flContents, VerifyUIdFragment.newInstance())
               // ?.addToBackStack("signIn")
            ?.commitNow()}
    }

    private fun verifyUId () : SignUp {
        return SignUp(edtUFirstName.text.toString(), edtUId.text.toString(), edtUSecret.text.toString(), PrefrenceManager(context!!).getFirebaseToken())
    }
}