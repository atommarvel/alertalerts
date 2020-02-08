package com.radiantmood.alertalerts.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.radiantmood.alertalerts.data.database.RuleDatabase
import dagger.Component
import javax.inject.Scope

@Scope
annotation class PerFragment

@PerFragment
@Component(
    dependencies = [AppComponent::class],
    modules =
    [
        ViewModelModule::class,
        ViewModelFactoryModule::class,
        FragmentActivityModule::class
    ]
)

interface FragmentComponent {
    fun viewModelProviderFactory(): ViewModelProvider.Factory
    fun fragmentActivity(): FragmentActivity
    fun ruleDb(): RuleDatabase

    fun inject(fragment: Fragment)
}