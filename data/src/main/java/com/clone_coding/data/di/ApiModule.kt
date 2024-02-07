package com.clone_coding.data.di

import com.clone_coding.data.db.remote.api.UpbitApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun upbitApi(retrofit: Retrofit): UpbitApi {
        return retrofit.create(UpbitApi::class.java)
    }


}