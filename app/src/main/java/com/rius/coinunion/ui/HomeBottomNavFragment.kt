package com.rius.coinunion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rius.coinunion.R
import com.rius.coinunion.ui.home.HomeFragment
import com.rius.coinunion.ui.profile.ProfileFragment
import com.rius.coinunion.ui.writing.WritingPagerFragment
import kotlinx.android.synthetic.main.fragment_home_bottom_nav.*

class HomeBottomNavFragment : Fragment() {

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

        switchPage(homeFragment)

        nav_view.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    switchPage(homeFragment)
                    true
                }
                R.id.navigation_writing -> {
                    switchPage(writingFragment)
                    true
                }
                R.id.navigation_profile -> {
                    switchPage(profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun switchPage(fragment: Fragment) {
        if (!fragment.isVisible) {
            childFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        }
    }

}
