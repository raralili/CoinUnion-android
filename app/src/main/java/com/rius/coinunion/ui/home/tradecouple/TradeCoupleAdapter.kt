package com.rius.coinunion.ui.home.tradecouple

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.rius.coinunion.AppExecutors
import com.rius.coinunion.R
import com.rius.coinunion.databinding.TradeCoupleListItemBinding
import com.rius.coinunion.db.entity.TradeCouple
import com.rius.coinunion.ui.common.MyListAdapter
import com.rius.coinunion.ui.common.diffCallback

class TradeCoupleAdapter(
    private val bindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private var checkCallback: ((TradeCouple, Boolean) -> Unit)?
) : MyListAdapter<TradeCouple, TradeCoupleListItemBinding>(
    appExecutors, diffCallback({ old, new ->
        old.symbol == new.symbol
    }, { old, new ->
        old.symbol == new.symbol
    })
) {

    lateinit var binding: TradeCoupleListItemBinding
    var previewCheckCount = 0

    override fun createBinding(parent: ViewGroup): TradeCoupleListItemBinding {
        val binding = DataBindingUtil.inflate<TradeCoupleListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.trade_couple_list_item,
            parent,
            false,
            bindingComponent
        )
        binding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.tradeCouple?.let {
                checkCallback?.invoke(it, isChecked)
            }
        }
        return binding
    }

    override fun onBind(binding: TradeCoupleListItemBinding, item: TradeCouple, position: Int) {
        binding.checkbox.isChecked = position <= previewCheckCount - 1
    }

    override fun bind(binding: TradeCoupleListItemBinding, item: TradeCouple) {
        binding.tradeCouple = item
    }
}