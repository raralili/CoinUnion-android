package com.rius.coinunion.ui.home.tradecouple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.orhanobut.logger.Logger
import com.rius.coinunion.AppExecutors
import com.rius.coinunion.R
import com.rius.coinunion.binding.FragmentBindingComponent
import com.rius.coinunion.db.entity.TradeCouple
import com.rius.coinunion.helper.autoCleared
import com.rius.coinunion.injector.Injectable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.trade_couple_fragment.*
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
        val recyclerView = root.recycler_view
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        adapter =
            TradeCoupleAdapter(bindingComponent, appExecutors) { coupleState, isChecked ->
                //Nothing needs to do at present time.
            }
        recyclerView.adapter = adapter
        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //列表数据
        val coupleState = mutableListOf<TradeCoupleState>()
        //查询到的本地自选交易对
        val localCouples = mutableListOf<TradeCouple>()

        //读取数据库中的自选交易对
        disposable.add(viewModel.loadLocalTradeCouples().subscribe { couples ->
            localCouples.addAll(couples)
            val convertedList = couples.map {
                TradeCoupleState(
                    it,
                    isChecked = true,
                    isLocal = true
                )
            }
            coupleState.addAll(convertedList)
        })
        //读取网络获取的交易对
        disposable.add(viewModel.getTradeCouples().subscribe({ couples ->
            //网络中的交易对和数据库中的有重复，先将其移除
            val rest = couples.toMutableList().apply {
                removeAll(localCouples)
            }
            coupleState.addAll(rest.map {
                TradeCoupleState(
                    it,
                    isChecked = false,
                    isLocal = false
                )
            })
            adapter.submitList(coupleState)
        }, { t ->
            Logger.e(t.message!!)
        }))


        //提交按钮
        title_bar.setEndAction {
            //挑出本地有的，如果没选择，就把本地的删除
            val data = adapter.currentList
            if (data.isNotEmpty()) {
                val local = data.filter { it.isLocal }
                local.forEach {
                    if (!it.isChecked) {
                        viewModel.deleteLocalTrade(it.tradeCouple).subscribe { success ->
                            Logger.d("TradeCoupleDelete:${success}")
                        }
                    }
                }
                //不是本地有的，又选择了，则添加
                val selected = data.filter { !it.isLocal && it.isChecked }
                if (selected.isNotEmpty()) {
                    viewModel.addCouple(*selected.map { it.tradeCouple }.toTypedArray()).subscribe {
                        findNavController().navigateUp()
                    }
                } else {
                    findNavController().navigateUp()
                }
            } else {
                findNavController().navigateUp()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
