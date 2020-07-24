package com.rius.coinunion.injector.module

import com.rius.coinunion.ui.home.HomeFragment
import com.rius.coinunion.ui.market.MarketFragment
import com.rius.coinunion.ui.writing.subs.WritingLatestFragment
import com.rius.coinunion.ui.writing.subs.WritingFragment
import com.rius.coinunion.ui.writing.subs.WritingStarFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMarketFragment(): MarketFragment

    @ContributesAndroidInjector
    abstract fun contributeWritingRecommendFragment(): WritingFragment

    @ContributesAndroidInjector
    abstract fun contributeWritingLatestFragment(): WritingLatestFragment

    @ContributesAndroidInjector
    abstract fun contributeWritingStarFragment(): WritingStarFragment
}