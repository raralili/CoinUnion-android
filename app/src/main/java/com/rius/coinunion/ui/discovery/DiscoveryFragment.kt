package com.rius.coinunion.ui.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.orhanobut.logger.Logger
import com.rius.coinunion.AppExecutors
import com.rius.coinunion.R
import com.rius.coinunion.binding.FragmentBindingComponent
import com.rius.coinunion.helper.autoCleared
import com.rius.coinunion.injector.Injectable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.discovery_fragment.*
import javax.inject.Inject

class DiscoveryFragment : Fragment(), Injectable {

    companion object {
        fun newInstance() = DiscoveryFragment()
    }

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<DiscoveryViewModel> { viewModelFactory }

    //    var adapter by autoCleared<DiscoveryListAdapter>()
    lateinit var adapter: DiscoveryListAdapter

    val bindingComponent = FragmentBindingComponent(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.discovery_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = recycler_view
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        adapter = DiscoveryListAdapter(bindingComponent, appExecutors)
        recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        disposable.add(
            viewModel.getRecommendUsers().subscribe(
                { users ->
                    adapter.submitList(users)
                }, { t ->
                    Logger.e(t.message!!)
                }
            )
        )
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

}
