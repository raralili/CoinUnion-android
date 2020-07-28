package com.rius.coinunion.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.orhanobut.logger.Logger
import com.rius.coinunion.R
import com.rius.coinunion.binding.FragmentBindingComponent
import com.rius.coinunion.databinding.ProfileFragmentBinding
import com.rius.coinunion.helper.autoCleared
import com.rius.coinunion.injector.Injectable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ProfileFragment : Fragment(), Injectable {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ProfileViewModel> { viewModelFactory }

    val dataBindingComponent = FragmentBindingComponent(this)
    var binding by autoCleared<ProfileFragmentBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<ProfileFragmentBinding>(
            inflater, R.layout.profile_fragment,
            container,
            false,
            dataBindingComponent
        )
        binding = dataBinding
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        disposable.add(
            viewModel.userProfile("10086").subscribe({ info ->
                binding.profileInfo = info
            }, { t ->
                Logger.e(t.message!!)
            })
        )

        disposable.add(
            viewModel.getRecommendUsers().subscribe({ users ->
                binding.recommendUsers = users
            }, { t ->
                Logger.e(t.message!!)
            })
        )
    }

}
