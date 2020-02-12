package com.radiantmood.alertalerts.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.radiantmood.alertalerts.ui.main.MainFragment
import com.radiantmood.alertalerts.ui.main.MainViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
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

@Module
class MainVMModule {
    @PerMainScreen
    @Provides
    fun mainVM(fragment: Fragment, vmFactory: ViewModelProvider.Factory): MainViewModel =
        ViewModelProvider(fragment, vmFactory)[MainViewModel::class.java]
}