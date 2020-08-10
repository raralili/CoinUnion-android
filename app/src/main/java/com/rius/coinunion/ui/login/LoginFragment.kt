package com.rius.coinunion.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.rius.coinunion.R
import com.rius.coinunion.injector.Injectable
import com.rius.coinunion.utils.KmUtils
import com.rius.coinunion.utils.RegexUtils
import kotlinx.android.synthetic.main.login_fragment.*
import javax.inject.Inject

class LoginFragment : Fragment(), Injectable {

    companion object {
        fun newInstance() = LoginFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<LoginViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_login.setOnClickListener {
            val phone = et_phone.text.toString().trim()
            val password = et_password.text.toString().trim()

            viewModel.signIn(phone, password)
        }
    }

}
