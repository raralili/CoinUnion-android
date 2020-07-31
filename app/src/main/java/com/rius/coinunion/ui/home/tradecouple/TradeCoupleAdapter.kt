package com.rius.coinunion.ui.home.tradecouple

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.rius.coinunion.AppExecutors
import com.rius.coinunion.R
import com.rius.coinunion.databinding.TradeCoupleListItemBinding
import com.rius.coinunion.entity.market.TradeCouple
import com.rius.coinunion.ui.common.MyListAdapter
import com.rius.coinunion.ui.common.diffCallback

class TradeCoupleAdapter(
    private val bindingComponent: DataBindingComponent,
    appExecutors: AppExecutors
) : MyListAdapter<TradeCouple, TradeCoupleListItemBinding>(
    appExecutors, diffCallback({ old, new ->
        old.coupleForView == new.coupleForView
    }, { old, new ->
        old.coupleForView == new.coupleForView
    })
) {
    override fun createBinding(parent: ViewGroup): TradeCoupleListItemBinding {
        val binding = DataBindingUtil.inflate<TradeCoupleListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.trade_couple_list_item,
            parent,
            false,
            bindingComponent
        )
        return binding
    }

    override fun bind(binding: TradeCoupleListItemBinding, item: TradeCouple) {
        binding.tradeCouple = item
    }
}