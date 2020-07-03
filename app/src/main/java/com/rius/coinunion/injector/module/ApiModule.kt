package com.rius.coinunion.injector.module

import com.rius.coinunion.api.spot.CommonApi
import com.rius.coinunion.injector.NetworkModule
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class ApiModule {

    @Provides
    @Singleton
    fun provideSpotCommonApi(retrofit: Retrofit): CommonApi {
        return retrofit.create(CommonApi::class.java)
    }
}