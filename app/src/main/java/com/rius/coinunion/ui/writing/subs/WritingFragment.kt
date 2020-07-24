package com.rius.coinunion.ui.writing.subs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.rius.coinunion.AppExecutors
import com.rius.coinunion.R
import com.rius.coinunion.binding.FragmentBindingComponent
import com.rius.coinunion.databinding.WritingListItemBinding
import com.rius.coinunion.entity.writing.WritingInfo
import com.rius.coinunion.injector.Injectable
import kotlinx.android.synthetic.main.writing_recommend_fragment.*
import javax.inject.Inject

const val WRITING_TYPE_KEY = "writing_type"
const val WRITING_TYPE_RECOMMEND = 1
const val WRITING_TYPE_LATEST = 2
const val WRITING_TYPE_STAR = 3

class WritingFragment private constructor() : Fragment(), Injectable {

    private var type: Int = 0

    companion object {

        fun newInstance(type: Int) = WritingFragment().apply {
            if (type in WRITING_TYPE_RECOMMEND..WRITING_TYPE_STAR) {
                arguments = Bundle().apply {
                    this.putInt(WRITING_TYPE_KEY, type)
                }
            } else throw IllegalArgumentException("unexpected type for WritingFragment")
        }
    }

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: WritingViewModel by viewModels { viewModelFactory }

//    private val adapter = WritingAdapter()

    private lateinit var adapter: WritingListAdapter

    var bindingComponent: DataBindingComponent = FragmentBindingComponent(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getInt(WRITING_TYPE_KEY)
        }
    }

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.writing_recommend_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = recycler_view
        val adapter = WritingListAdapter(bindingComponent, appExecutors)
        this.adapter = adapter
        recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        adapter.setNewInstance(viewModel.getListData(type))
        val src = viewModel.getListData(type)
        adapter.submitList(src)
    }

    inner class WritingAdapter :
        BaseQuickAdapter<WritingInfo, BaseDataBindingHolder<WritingListItemBinding>>(R.layout.writing_list_item) {

        override fun convert(
            holder: BaseDataBindingHolder<WritingListItemBinding>,
            item: WritingInfo
        ) {
            val binding = holder.dataBinding!!
            binding.info = item
            Glide.with(this@WritingFragment)
                .load(item.imgUrl)
                .into(binding.ivMain)
            binding.executePendingBindings()
        }
    }
}
