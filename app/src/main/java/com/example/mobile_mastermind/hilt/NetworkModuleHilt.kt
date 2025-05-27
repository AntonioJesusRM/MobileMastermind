package com.example.mobile_mastermind.hilt

import com.example.mobile_mastermind.data.repository.remote.backend.ApiService
import com.example.mobile_mastermind.data.repository.remote.backend.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModuleHilt {
    @Provides
    @Singleton
    fun provideApiService(retrofitClient: RetrofitClient): ApiService {
        return retrofitClient.retrofit.create(ApiService::class.java)
    }
}