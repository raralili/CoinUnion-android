package com.rius.coinunion.ui.writing

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rius.coinunion.ui.writing.subs.WRITING_TYPE_LATEST
import com.rius.coinunion.ui.writing.subs.WRITING_TYPE_RECOMMEND
import com.rius.coinunion.ui.writing.subs.WRITING_TYPE_STAR
import com.rius.coinunion.ui.writing.subs.WritingFragment

const val WRITING_RECOMMEND_INDEX = 0
const val WRITING_LATEST_INDEX = 1
const val WRITING_STAR_INDEX = 2

class WritingPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentCreators: Map<Int, () -> Fragment> = mapOf(
        WRITING_RECOMMEND_INDEX to { WritingFragment.newInstance(WRITING_TYPE_RECOMMEND) },
        WRITING_LATEST_INDEX to { WritingFragment.newInstance(WRITING_TYPE_LATEST) },
        WRITING_STAR_INDEX to { WritingFragment.newInstance(WRITING_TYPE_STAR) }
    )

    override fun getItemCount() = tabFragmentCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

}