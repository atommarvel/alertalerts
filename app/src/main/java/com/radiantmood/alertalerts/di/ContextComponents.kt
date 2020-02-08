package com.radiantmood.alertalerts.di

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.radiantmood.alertalerts.data.database.RuleDatabase
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// TODO: look into purpose for this
@Singleton
@Component(modules = [ContextModule::class, DatabaseModule::class])
interface AppComponent {
    fun ruleDb(): RuleDatabase

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