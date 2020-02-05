package com.radiantmood.alertalerts.core

import android.app.Application
import com.facebook.stetho.Stetho
import com.radiantmood.alertalerts.di.AppComponent
import com.radiantmood.alertalerts.di.ContextModule
import com.radiantmood.alertalerts.di.DaggerAppComponent


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDagger()
        Stetho.initializeWithDefaults(this)
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}