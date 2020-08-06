package com.rius.coinunion.ui.writing

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.rius.coinunion.R
import com.rius.coinunion.ui.HomeBottomNavFragmentDirections
import com.rius.coinunion.utils.KmUtils
import kotlinx.android.synthetic.main.writing_edit_fragment.view.*
import kotlinx.android.synthetic.main.writing_pager_fragment.view.*
import permissions.dispatcher.*

@RuntimePermissions
class WritingPagerFragment : Fragment() {

    companion object {
        fun newInstance() = WritingPagerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.writing_pager_fragment, container, false)
        val tabLayout = root.tab_layout
        val viewPager = root.view_pager
        val floatingButton = root.floating_button


        viewPager.adapter = WritingPagerAdapter(this)
        viewPager.isSaveEnabled = false

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitle(position)
        }.attach()

        floatingButton.setOnClickListener { v ->
            directWritingEditPage(v)
        }
        return root
    }

    @NeedsPermission(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun directWritingEditPage(v: View) {
        val directions =
            HomeBottomNavFragmentDirections.actionHomeBottomNavFragmentToWritingEditFragment()
        v.findNavController().navigate(directions)
    }

    @OnShowRationale(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun showPermissionDialog(request: PermissionRequest) {
        AlertDialog.Builder(this.requireContext()).setMessage("应用正在申请一下权限")
            .setPositiveButton("确定") { _, _ -> request.proceed() }
            .setNegativeButton("取消") { _, _ -> request.cancel() }.show()
    }

    @OnPermissionDenied(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun onPermissionDenied() {
        KmUtils.toast("权限被拒绝，无法发文")
    }

    @OnNeverAskAgain(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun onPermissionNever() {
        KmUtils.toast("权限被拒绝，无法发文。请到系统设置中手动打开")
    }

    private fun tabTitle(position: Int): String? {
        return when (position) {
            WRITING_RECOMMEND_INDEX -> getString(R.string.tab_writing_recommend)
            WRITING_LATEST_INDEX -> getString(R.string.tab_writing_latest)
            WRITING_STAR_INDEX -> getString(R.string.tab_writing_star)
            else -> null
        }
    }
}
