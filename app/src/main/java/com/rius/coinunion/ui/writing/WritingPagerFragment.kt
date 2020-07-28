package com.rius.coinunion.ui.writing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.rius.coinunion.R
import kotlinx.android.synthetic.main.writing_pager_fragment.view.*

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

        viewPager.adapter = WritingPagerAdapter(this)
        viewPager.isSaveEnabled = false

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitle(position)
        }.attach()
        return root
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
