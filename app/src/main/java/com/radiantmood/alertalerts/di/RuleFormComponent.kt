package com.radiantmood.alertalerts.di

import com.radiantmood.alertalerts.ui.ruleform.RuleFormFragment
import dagger.Component
import javax.inject.Scope

@Scope
annotation class PerRuleForm

@PerRuleForm
@Component(
    dependencies = [FragmentComponent::class]
)
interface RuleFormComponent {
    fun inject(fragment: RuleFormFragment)
}
