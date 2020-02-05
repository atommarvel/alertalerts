package com.radiantmood.alertalerts.di

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
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
class FragmentActivityModule(private val fragmentActivity: FragmentActivity) {
    @Provides
    fun fragmentActivity(): FragmentActivity = fragmentActivity
}

@Module
class ContextModule(private val appContext: Context) {
    @Provides
    fun appContext(): Context = appContext
}