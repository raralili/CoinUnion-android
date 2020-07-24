package com.rius.coinunion.ui.writing.subs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.rius.coinunion.AppExecutors
import com.rius.coinunion.R
import com.rius.coinunion.databinding.WritingListItemBinding
import com.rius.coinunion.entity.writing.WritingInfo
import com.rius.coinunion.ui.common.DataBoundListAdapter

class WritingListAdapter(
    private val bindingComponent: DataBindingComponent,
    appExecutors: AppExecutors
) : DataBoundListAdapter<WritingInfo, WritingListItemBinding>(
    appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<WritingInfo>() {
        override fun areItemsTheSame(oldItem: WritingInfo, newItem: WritingInfo): Boolean {
            return oldItem.imgUrl == newItem.imgUrl
        }

        override fun areContentsTheSame(oldItem: WritingInfo, newItem: WritingInfo): Boolean {
            return oldItem.imgUrl == newItem.imgUrl && oldItem.title == newItem.title
        }

    }
) {
    override fun createBinding(parent: ViewGroup): WritingListItemBinding {
        val binding = DataBindingUtil.inflate<WritingListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.writing_list_item,
            parent,
            false,
            bindingComponent
        )
        return binding
    }

    override fun bind(binding: WritingListItemBinding, item: WritingInfo) {
        binding.info = item
    }
}