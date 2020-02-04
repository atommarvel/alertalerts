package com.radiantmood.alertalerts.di

import com.radiantmood.alertalerts.ui.main.MainFragment
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
        ViewModelFactoryModule::class
    ]
)
interface FragmentComponent {
    fun inject(fragment: MainFragment)
}