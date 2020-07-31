package com.rius.coinunion.ui.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.rius.coinunion.AppExecutors
import com.rius.coinunion.R
import com.rius.coinunion.databinding.UserListItemBinding
import com.rius.coinunion.entity.user.UserInfo
import com.rius.coinunion.ui.common.MyListAdapter
import com.rius.coinunion.ui.common.diffCallback

class UserListAdapter(
    private val bindingComponent: DataBindingComponent,
    appExecutors: AppExecutors
) : MyListAdapter<UserInfo, UserListItemBinding>(
    appExecutors,
    diffCallback({ old, new ->
        old.privateInfo.uid == new.privateInfo.uid
    }, { old, new ->
        old.privateInfo.uid == new.privateInfo.uid && old.avatar == new.avatar && old.name == new.name
    })
) {

    override fun createBinding(parent: ViewGroup): UserListItemBinding {
        val binding = DataBindingUtil.inflate<UserListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.user_list_item,
            parent,
            false,
            bindingComponent
        )
        return binding
    }

    override fun bind(binding: UserListItemBinding, item: UserInfo) {
        binding.userInfo = item
    }
}