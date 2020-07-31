package com.rius.coinunion.ui.writing.subs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.rius.coinunion.AppExecutors
import com.rius.coinunion.R
import com.rius.coinunion.databinding.WritingListItemBinding
import com.rius.coinunion.entity.writing.WritingInfo
import com.rius.coinunion.ui.common.MyListAdapter
import com.rius.coinunion.ui.common.diffCallback

class WritingListAdapter(
    private val bindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((View, WritingInfo) -> Unit)?
) : MyListAdapter<WritingInfo, WritingListItemBinding>(
    appExecutors,
    diffCallback({ old, new ->
        old.imgUrl == old.imgUrl
    }, { old, new ->
        old.imgUrl == new.imgUrl && old.title == new.title
    })
) {
    override fun createBinding(parent: ViewGroup): WritingListItemBinding {
        val binding = DataBindingUtil.inflate<WritingListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.writing_list_item,
            parent,
            false,
            bindingComponent
        )
        binding.root.setOnClickListener { v ->
            binding.info?.let {
                callback?.invoke(v, it)
            }
        }
        return binding
    }

    override fun bind(binding: WritingListItemBinding, item: WritingInfo) {
        binding.info = item
    }
}