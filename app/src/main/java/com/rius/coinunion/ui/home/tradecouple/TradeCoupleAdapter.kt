package com.rius.coinunion.ui.home.tradecouple

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.rius.coinunion.AppExecutors
import com.rius.coinunion.R
import com.rius.coinunion.databinding.TradeCoupleListItemBinding
import com.rius.coinunion.ui.common.MyListAdapter
import com.rius.coinunion.ui.common.diffCallback

class TradeCoupleAdapter(
    private val bindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private var checkCallback: ((TradeCoupleState, Boolean) -> Unit)?
) : MyListAdapter<TradeCoupleState, TradeCoupleListItemBinding>(
    appExecutors, diffCallback(itemSame = { old, new ->
        old.tradeCouple == new.tradeCouple
    }, contentsSame = { old, new ->
        old.tradeCouple == new.tradeCouple && old.isChecked == new.isChecked && old.isLocal == new.isLocal
    })
) {

    lateinit var binding: TradeCoupleListItemBinding
    private val checkStates = SparseBooleanArray()

    override
    fun createBinding(parent: ViewGroup): TradeCoupleListItemBinding {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.trade_couple_list_item,
            parent,
            false,
            bindingComponent
        )
        return binding
    }

    override fun onBind(
        binding: TradeCoupleListItemBinding,
        item: TradeCoupleState,
        position: Int
    ) {
        binding.checkbox.tag = position
        binding.root.setOnClickListener {
            binding.checkbox.isChecked = !binding.checkbox.isChecked
        }
        binding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            val index = buttonView.tag as Int
            if (isChecked) {
                checkStates.put(index, true)
                binding.coupleState?.let { it.isChecked = true }
            } else {
                binding.coupleState?.let { it.isChecked = false }
                checkStates.delete(index)
            }
        }
        if (item.isChecked) {
            checkStates.put(position, true)
        }
        binding.checkbox.isChecked = checkStates[position, false]
    }

    override fun bind(binding: TradeCoupleListItemBinding, item: TradeCoupleState) {
        binding.coupleState = item
    }
}