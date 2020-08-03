package com.rius.coinunion.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.rius.coinunion.R
import com.rius.coinunion.databinding.HomeListItemBinding
import com.rius.coinunion.db.LocalDefaultTradeCouples
import com.rius.coinunion.db.entity.TradeCouple
import com.rius.coinunion.entity.market.CoinInfo
import com.rius.coinunion.injector.Injectable
import com.rius.coinunion.ui.HomeBottomNavFragmentDirections
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.NumberFormat
import javax.inject.Inject


class HomeFragment : Fragment(), Injectable {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    private lateinit var root: View

    val adapter = CoinAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)

        val floatingButton = root.floating_button
        floatingButton.setOnClickListener {
            val directions =
                HomeBottomNavFragmentDirections.actionHomeBottomNavFragmentToTradeCoupleFragment()
            it.findNavController().navigate(directions)
        }

        val recyclerView = root.recyclerView
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        adapter.setOnItemClickListener { _, view, position ->
            view.findNavController().navigate(
                R.id.action_homeBottomNavFragment_to_marketFragment,
                Bundle().apply {
                    putString(
                        "name", this@HomeFragment.adapter.data[position].name
                    )
                }
            )
        }
        recyclerView.adapter = adapter
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var selfChoiceTradeCoupleSize = 0
        val coins = mutableListOf<CoinInfo>()
        val percentFormat = NumberFormat.getPercentInstance()
        percentFormat.maximumFractionDigits = 2
        percentFormat.minimumFractionDigits = 2

        viewModel.onConnectedWebSocket { _, _ ->
            disposable.add(viewModel.loadLocalTradeCouples().subscribe { tradeCouples ->
                val couples = mutableListOf<TradeCouple>()
                if (tradeCouples.isNotEmpty()) {
                    couples.addAll(tradeCouples)
                } else {
                    val defaultTradeCouple = LocalDefaultTradeCouples.get()
                    viewModel.insertTradeCouples(*defaultTradeCouple).subscribe {
                        couples.addAll(defaultTradeCouple)
                    }
                }
                if (couples.isNotEmpty()) {
                    couples.forEach {
                        viewModel.addTopic(
                            mapOf(
                                Pair("sub", "market.${it.symbol}.kline.1day"),
                                Pair("id", "rius")
                            )
                        )
                    }
                    selfChoiceTradeCoupleSize = couples.size
                    viewModel.subscribe()
                }
            })
        }

        viewModel.onSocketData { coinInfo ->
            requireActivity().runOnUiThread {
                if (coins.size < selfChoiceTradeCoupleSize) {
                    if (!coins.map { it.name }.contains(coinInfo.name)) {
                        coins.add(coinInfo)
                    }
                    if (coins.size == selfChoiceTradeCoupleSize) {
                        adapter.setNewInstance(coins)
                    }
                } else {
                    val index = coins.map { it.name }.indexOf(coinInfo.name)
                    val old = coins[index]
                    val oldPercent = percentFormat.format(old.percent)
                    val currentPercent = percentFormat.format(coinInfo.percent)
                    if (oldPercent != currentPercent) {
                        adapter.setData(index, coinInfo)
                    }
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
//        loading_view.show()
        viewModel.run {
            registerMessageListener()
            connectWebSocket()
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.run {
            unregisterMessageListener()
            disconnectWebSocket()
        }
    }

    inner class CoinAdapter :
        BaseQuickAdapter<CoinInfo, BaseDataBindingHolder<HomeListItemBinding>>(R.layout.home_list_item) {

        override fun convert(holder: BaseDataBindingHolder<HomeListItemBinding>, item: CoinInfo) {
            val binding = holder.dataBinding!!
            binding.coinInfo = item
            binding.executePendingBindings()
        }
    }
}
