package com.rius.coinunion.injector.module

import com.rius.coinunion.MainActivity
import com.rius.coinunion.ui.SplashActivity
import com.rius.coinunion.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [LoginFragmentsModule::class])
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity
}