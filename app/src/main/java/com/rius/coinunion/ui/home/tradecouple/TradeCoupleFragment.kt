package com.rius.coinunion.ui.home.tradecouple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
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

    //保存选中的交易对的状态。完成时根据状态添加到数据库
    var selectedItem by autoCleared<MutableList<TradeCouple>>()

    //保存最初的交易对的check状态。如果完成时没选中某一条交易对，则应从数据库中删除
    var originItemStateMap by autoCleared<MutableMap<TradeCouple, Int>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.trade_couple_fragment, container, false)
        selectedItem = mutableListOf()

        originItemStateMap = mutableMapOf()
        adapter = TradeCoupleAdapter(bindingComponent, appExecutors) { tradeCouple, isChecked ->
            if (isChecked) {
                if (!selectedItem.contains(tradeCouple)) selectedItem.add(tradeCouple)
                if (originItemStateMap.containsKey(tradeCouple)) {
                    originItemStateMap[tradeCouple] = 1
                }
            } else {
                if (selectedItem.contains(tradeCouple)) selectedItem.remove(tradeCouple)
                if (originItemStateMap.containsKey(tradeCouple)) {
                    originItemStateMap[tradeCouple] = 0
                }
            }
        }
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

        val tradeCouple = mutableListOf<TradeCouple>()
        var localTradeCoupleSize = 0
        disposable.add(viewModel.loadLocalTradeCouples().subscribe {
            if (it.isNotEmpty()) {
                tradeCouple.addAll(it)
                selectedItem.addAll(tradeCouple)
                localTradeCoupleSize = tradeCouple.size

                tradeCouple.forEach { couple ->
                    originItemStateMap[couple] = 1
                }
            }
        })

        disposable.add(viewModel.getTradeCouples().subscribe({ result ->
            val temp = result.toMutableList()
            if (tradeCouple.isNotEmpty()) {
                temp.removeAll(tradeCouple)
            }
            tradeCouple.addAll(temp)
            if (localTradeCoupleSize != 0) {
                adapter.previewCheckCount = localTradeCoupleSize
            }
            adapter.submitList(tradeCouple)

        }, { t ->
            Logger.e(t.message!!)
        }))

        tv_action_finish.setOnClickListener { v ->
            if (originItemStateMap.isNotEmpty()) {
                for (entry in originItemStateMap) {
                    if (entry.value == 0) {
                        val couple = entry.key
                        viewModel.deleteLocalTrade(couple).subscribe { success, t ->
                            Logger.d("[deleteLocalCouple]:${success}")
                        }
                    } else {
                        //如果初始交易对状态未改变，应该将该交易对从selectedItem中删掉，否则会造成重复添加
                        selectedItem.remove(entry.key)
                    }
                }
            }

            if (selectedItem.isNotEmpty()) {
                viewModel.addCouple(*selectedItem.toTypedArray()).subscribe {
                    v.findNavController().navigateUp()
                }
            } else {
                v.findNavController().navigateUp()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

}
