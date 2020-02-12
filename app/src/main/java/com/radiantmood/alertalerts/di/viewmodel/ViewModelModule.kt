package com.radiantmood.alertalerts.di.viewmodel

import androidx.lifecycle.ViewModel
import com.radiantmood.alertalerts.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(RuleFormViewModel::class)
//    abstract fun bindRuleFormViewModel(ruleFormViewModel: RuleFormViewModel): ViewModel
}