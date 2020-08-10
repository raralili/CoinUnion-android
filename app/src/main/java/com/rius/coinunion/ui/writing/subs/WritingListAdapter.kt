package com.rius.coinunion.ui.writing.subs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.rius.coinunion.AppExecutors
import com.rius.coinunion.R
import com.rius.coinunion.databinding.WritingListItemBinding
import com.rius.coinunion.db.entity.WritingInfo
import com.rius.coinunion.ui.common.MyListAdapter
import com.rius.coinunion.ui.common.diffCallback

class WritingListAdapter(
    private val bindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((View, WritingInfo) -> Unit)?
) : MyListAdapter<WritingInfo, WritingListItemBinding>(
    appExecutors,
    diffCallback({ old, new ->
        old.id == new.id
    }, { old, new ->
        old.id == new.id && old.content == new.content
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
        if (item.imgs == null || item.imgs.isEmpty()) {
            binding.ivMain.visibility = View.GONE
        } else {
            Glide.with(bindingComponent.fragmentBindingAdapter.fragment).load(item.imgs[0])
                .into(binding.ivMain)
        }
        binding.info = item
    }
}