package com.rius.coinunion.injector.module

import com.rius.coinunion.ui.login.LoginFragment
import com.rius.coinunion.ui.login.RegisterFragment
import com.rius.coinunion.ui.login.ResetPasswordFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginFragmentsModule {

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    abstract fun contributeResetPasswordFragment(): ResetPasswordFragment
}