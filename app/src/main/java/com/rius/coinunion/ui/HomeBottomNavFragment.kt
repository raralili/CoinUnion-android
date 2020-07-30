package com.rius.coinunion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.rius.coinunion.R
import com.rius.coinunion.ui.home.HomeFragment
import com.rius.coinunion.ui.profile.ProfileFragment
import com.rius.coinunion.ui.writing.WritingPagerFragment
import kotlinx.android.synthetic.main.fragment_home_bottom_nav.*

class HomeBottomNavFragment : Fragment() {

    var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_bottom_nav, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpUi()
    }

    private fun setUpUi() {
        val homeFragment = HomeFragment.newInstance()
        val writingFragment = WritingPagerFragment.newInstance()
        val profileFragment = ProfileFragment.newInstance()

        val pages = listOf(homeFragment, writingFragment, profileFragment)
        val fragment = when (currentPage) {
            0 -> homeFragment
            1 -> writingFragment
            2 -> profileFragment
            else -> null
        } ?: return

        switchPage(fragment)

        nav_view.setOnNavigationItemSelectedListener { menuItem ->
            switchPageById(menuItem.itemId, pages)
            true
        }
    }

    private fun switchPage(fragment: Fragment) {
        if (!fragment.isVisible) {
            childFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        }
    }

    private fun switchPageById(@IdRes id: Int, pages: List<Fragment>) {
        when (id) {
            R.id.navigation_home -> {
                currentPage = 0
                switchPage(pages[0])
            }
            R.id.navigation_writing -> {
                currentPage = 1
                switchPage(pages[1])
            }
            R.id.navigation_profile -> {
                currentPage = 2
                switchPage(pages[2])
            }
        }
    }

}
