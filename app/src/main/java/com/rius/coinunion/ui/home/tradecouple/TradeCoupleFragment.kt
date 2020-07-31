package com.rius.coinunion.ui.home.tradecouple

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
import kotlinx.android.synthetic.main.trade_couple_fragment.view.*
import javax.inject.Inject

class TradeCoupleFragment : Fragment(), Injectable {

    companion object {
        fun newInstance() = TradeCoupleFragment()
    }

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<TradeCoupleViewModel> { viewModelFactory }

    @Inject
    lateinit var appExecutors: AppExecutors
    val bindingComponent = FragmentBindingComponent(this)
    var adapter by autoCleared<TradeCoupleAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.trade_couple_fragment, container, false)
        adapter = TradeCoupleAdapter(bindingComponent, appExecutors)
        val recyclerView = root.recycler_view
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.adapter = adapter
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        disposable.add(viewModel.getTradeCouples().subscribe({ result ->
            adapter.submitList(result)
        }, { t ->
            Logger.e(t.message!!)
        }))
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

}
