package com.rius.coinunion.injector.module

import com.rius.coinunion.ui.home.HomeFragment
import com.rius.coinunion.ui.writing.WritingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMovementFragment(): WritingFragment
}