package com.example.mobile_mastermind.hilt

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModuleHilt {
    @Provides
    @Singleton
    fun provideBaseProjectApplication(application: Application): SimpleApplication {
        return application as SimpleApplication
    }
}