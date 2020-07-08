package com.rius.coinunion.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.rius.coinunion.R
import com.rius.coinunion.databinding.HomeListItemBinding
import com.rius.coinunion.db.SelfChoice
import com.rius.coinunion.entity.spot.CoinInfo
import com.rius.coinunion.injector.Injectable
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.NumberFormat
import javax.inject.Inject


class HomeFragment : Fragment(), Injectable {


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
        return root
    }


    private var addIndex = 0
    private var isAllAdded = false


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView = root.recyclerView

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.adapter = adapter

        val infos = mutableListOf<CoinInfo>()

        viewModel.onSocketData { coinInfo ->
            requireActivity().runOnUiThread {
                val selfChoiceIndex = SelfChoice.getIndex(coinInfo.name)
                if (!isAllAdded) {
                    if (selfChoiceIndex == addIndex) {
                        infos.add(coinInfo)
                        if (addIndex == SelfChoice.getCount() - 1) {
                            addIndex = 0
                            isAllAdded = true
                            adapter.setNewInstance(infos)
                        } else {
                            addIndex++
                        }
                    }
                } else {
                    val old = adapter.getItem(selfChoiceIndex)
                    val percentFormat = NumberFormat.getPercentInstance()
                    percentFormat.maximumFractionDigits = 2
                    percentFormat.minimumFractionDigits = 2
                    val oldPercent = percentFormat.format(old.percent)
                    val currentPercent = percentFormat.format(coinInfo.percent)
                    if (oldPercent != currentPercent) {
                        adapter.setData(selfChoiceIndex, coinInfo)
                    }
                }
            }
        }
    }



    override fun onStart() {
        super.onStart()

        viewModel.connectWebSocket()
    }

    override fun onStop() {
        super.onStop()
        viewModel.disconnectWebSocket()
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
