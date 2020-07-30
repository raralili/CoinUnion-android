package com.rius.coinunion.ui.userlist

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
import com.rius.coinunion.injector.Injectable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.user_list_fragment.*
import javax.inject.Inject

class UserListFragment : Fragment(), Injectable {

    companion object {
        fun newInstance() = UserListFragment()
    }

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<UserListViewModel> { viewModelFactory }

    //    var adapter by autoCleared<DiscoveryListAdapter>()
    lateinit var adapter: UserListAdapter

    val bindingComponent = FragmentBindingComponent(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.user_list_fragment, container, false)
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
        adapter = UserListAdapter(bindingComponent, appExecutors)
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
