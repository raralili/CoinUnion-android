package com.rius.coinunion.ui.writing.edit

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.rius.coinunion.AppExecutors
import com.rius.coinunion.R
import com.rius.coinunion.binding.FragmentBindingComponent
import com.rius.coinunion.helper.autoCleared
import com.rius.coinunion.injector.Injectable
import com.rius.coinunion.utils.KmUtils
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import kotlinx.android.synthetic.main.writing_edit_fragment.view.*
import javax.inject.Inject


const val REQUEST_CODE = 320

class WritingEditFragment : Fragment(), Injectable {

    companion object {
        fun newInstance() = WritingEditFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private val viewModel by viewModels<WritingEditViewModel> { viewModelFactory }

    var adapter by autoCleared<WritingEditGridAdapter>()

    val fragmentBindingComponent = FragmentBindingComponent(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.writing_edit_fragment, container, false)
        val editTitle = root.et_title
        val editContent = root.et_content
        val btnPublish = root.btn_publish
        val btnImgPick = root.iv_img_pick

        val gridView = root.recycler_view

        adapter = WritingEditGridAdapter(appExecutors, fragmentBindingComponent)

        gridView.adapter = adapter

        btnImgPick.setOnClickListener {
            Matisse.from(this)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(9)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .theme(R.style.Matisse_Zhihu)
                .imageEngine(GlideEngine())
                .forResult(REQUEST_CODE)
        }

        btnPublish.setOnClickListener { v ->
            if (TextUtils.isEmpty(editTitle.text.toString().trim())) {
                KmUtils.toast(resources.getString(R.string.fragment_writing_edit_title_hint))
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(editContent.text.toString().trim())) {
                KmUtils.toast(resources.getString(R.string.fragment_writing_edit_content_hint))
                return@setOnClickListener
            }
            v.findNavController().navigateUp()
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImg = Matisse.obtainResult(data)
            val res = mutableListOf<WritingEditGridEntity>()
            selectedImg.forEach {
                res.add((WritingEditGridEntity(it)))
            }
            adapter.submitList(res)
        }
    }

}
