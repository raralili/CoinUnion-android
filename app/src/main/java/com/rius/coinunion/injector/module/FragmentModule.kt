package com.rius.coinunion.injector.module

import com.rius.coinunion.ui.userlist.UserListFragment
import com.rius.coinunion.ui.home.HomeFragment
import com.rius.coinunion.ui.market.MarketFragment
import com.rius.coinunion.ui.profile.ProfileFragment
import com.rius.coinunion.ui.writing.detail.WritingDetailFragment
import com.rius.coinunion.ui.writing.subs.WritingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMarketFragment(): MarketFragment

    @ContributesAndroidInjector
    abstract fun contributeWritingFragment(): WritingFragment

    @ContributesAndroidInjector
    abstract fun contributeWritingDetailFragment(): WritingDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributeDiscoveryFragment(): UserListFragment
}