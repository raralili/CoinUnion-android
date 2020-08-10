package com.rius.coinunion.ui.writing.subs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.orhanobut.logger.Logger
import com.rius.coinunion.AppExecutors
import com.rius.coinunion.R
import com.rius.coinunion.binding.FragmentBindingComponent
import com.rius.coinunion.injector.Injectable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.writing_fragment.*
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

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: WritingViewModel by viewModels { viewModelFactory }

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
        root = inflater.inflate(R.layout.writing_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = recycler_view
        val adapter = WritingListAdapter(bindingComponent, appExecutors) { _, info ->
            //            val directions = HomeBottomNavFragmentDirections.actionHomeBottomNavFragmentToWritingDetailFragment()
            findNavController().navigate(
                R.id.action_homeBottomNavFragment_to_writingDetailFragment,
                Bundle().apply {
                    putString("id", info.id)
                }
            )
        }
        this.adapter = adapter
        recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        disposable.addAll(viewModel.getListData(type).subscribe({ src ->
            adapter.submitList(src)
        }, { t -> Logger.e(t.message!!) }))
    }
}