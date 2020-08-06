package com.rius.coinunion.ui.writing.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.rius.coinunion.AppExecutors
import com.rius.coinunion.R
import com.rius.coinunion.binding.FragmentBindingComponent
import com.rius.coinunion.databinding.WritingEditFragmentGridItemBinding
import com.rius.coinunion.ui.common.MyListAdapter
import com.rius.coinunion.ui.common.diffCallback

class WritingEditGridAdapter(
    appExecutors: AppExecutors,
    val fragmentBindingComponent: FragmentBindingComponent
) :
    MyListAdapter<WritingEditGridEntity, WritingEditFragmentGridItemBinding>(appExecutors,
        diffCallback = diffCallback(
            itemSame = { old, new ->
                old.uri == new.uri
            }, contentsSame = { old, new ->
                old.uri == new.uri
            })
    ) {
    override fun createBinding(parent: ViewGroup): WritingEditFragmentGridItemBinding {
        val binding = DataBindingUtil.inflate<WritingEditFragmentGridItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.writing_edit_fragment_grid_item,
            parent,
            false,
            fragmentBindingComponent
        )
        return binding
    }

    override fun bind(binding: WritingEditFragmentGridItemBinding, item: WritingEditGridEntity) {
        binding.data = item
    }
}