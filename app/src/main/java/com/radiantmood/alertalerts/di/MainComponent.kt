package com.radiantmood.alertalerts.di

import com.radiantmood.alertalerts.ui.main.MainFragment
import dagger.Component
import javax.inject.Scope

@Scope
annotation class PerMainScreen

@PerMainScreen
@Component(
    dependencies = [FragmentComponent::class]
)
interface MainComponent {
    fun inject(fragment: MainFragment)
}