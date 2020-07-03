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
import com.orhanobut.logger.Logger
import com.rius.coinunion.R
import com.rius.coinunion.databinding.HomeListItemBinding
import com.rius.coinunion.entity.spot.CoinInfo
import com.rius.coinunion.injector.Injectable
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import javax.inject.Inject


class HomeFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView = root.recyclerView
        val adapter = CoinAdapter()
        val data = viewModel.getData()

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.adapter = adapter

        viewModel.connectWebSocket()

        viewModel.onSocketData { result ->
            tv_data.text = result
        }

        adapter.setNewInstance(data)
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
