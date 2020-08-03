package com.rius.coinunion.injector

import android.app.Application
import com.rius.coinunion.MyApp
import com.rius.coinunion.injector.module.ActivityModule
import com.rius.coinunion.injector.module.ApiModule
import com.rius.coinunion.injector.module.VMModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, ActivityModule::class, MyModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: MyApp)
}