package com.radiantmood.alertalerts.di

import android.app.Application
import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class])
interface AppComponent {
    fun inject(app: Application)
}

@Module
class ContextModule(private val appContext: Context) {
    @Provides
    fun appContext(): Context = appContext
}