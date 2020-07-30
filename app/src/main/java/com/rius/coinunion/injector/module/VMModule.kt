package com.rius.coinunion.injector.module

import androidx.lifecycle.ViewModel
import com.rius.coinunion.injector.ViewModelFactoryModule
import com.rius.coinunion.injector.ViewModelKey
import com.rius.coinunion.ui.SplashViewModel
import com.rius.coinunion.ui.userlist.UserListViewModel
import com.rius.coinunion.ui.home.HomeViewModel
import com.rius.coinunion.ui.market.MarketViewModel
import com.rius.coinunion.ui.profile.ProfileViewModel
import com.rius.coinunion.ui.writing.detail.WritingDetailViewModel
import com.rius.coinunion.ui.writing.subs.WritingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
abstract class VMModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(vm: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(vm: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MarketViewModel::class)
    abstract fun bindMarketViewModel(vm: MarketViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WritingViewModel::class)
    abstract fun bindWritingRecommendViewModel(vm: WritingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WritingDetailViewModel::class)
    abstract fun bindWritingDetailViewModel(vm: WritingDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(vm: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    abstract fun bindDiscoveryViewModel(vm: UserListViewModel): ViewModel

}