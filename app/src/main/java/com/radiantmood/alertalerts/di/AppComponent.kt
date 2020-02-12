package com.radiantmood.alertalerts.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.radiantmood.alertalerts.core.App
import com.radiantmood.alertalerts.data.database.RuleDatabase
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [AppContextModule::class, DatabaseModule::class, PrefsModule::class])
interface AppComponent {
    fun ruleDatabase(): RuleDatabase
    fun appContext(): Context
    fun prefs(): SharedPreferences

    fun inject(app: Application)
}

@Module
class AppContextModule(private val app: App) {
    @Provides
    fun appContext(): Context = app
}