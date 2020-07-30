package com.rius.coinunion.ui.discovery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.rius.coinunion.AppExecutors
import com.rius.coinunion.R
import com.rius.coinunion.databinding.DiscoveryListItemBinding
import com.rius.coinunion.entity.user.UserInfo
import com.rius.coinunion.ui.common.DataBoundListAdapter

class DiscoveryListAdapter(
    private val bindingComponent: DataBindingComponent,
    appExecutors: AppExecutors
) : DataBoundListAdapter<UserInfo, DiscoveryListItemBinding>(
    appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<UserInfo>() {
        override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
            return oldItem.privateInfo.uid == newItem.privateInfo.uid
        }

        override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
            return oldItem.privateInfo.uid == newItem.privateInfo.uid && oldItem.avatar == newItem.avatar && oldItem.name == newItem.name
        }

    }) {

    override fun createBinding(parent: ViewGroup): DiscoveryListItemBinding {
        val binding = DataBindingUtil.inflate<DiscoveryListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.discovery_list_item,
            parent,
            false,
            bindingComponent
        )
        return binding
    }

    override fun bind(binding: DiscoveryListItemBinding, item: UserInfo) {
        binding.userInfo = item
    }
}