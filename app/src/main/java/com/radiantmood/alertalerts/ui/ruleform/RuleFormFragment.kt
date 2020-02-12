package com.radiantmood.alertalerts.ui.ruleform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.radiantmood.alertalerts.R
import com.radiantmood.alertalerts.core.BaseFragment
import com.radiantmood.alertalerts.di.DaggerRuleFormComponent

class RuleFormFragment : BaseFragment() {

    private val viewModel: RuleFormViewModel by lazy { getViewModel(RuleFormViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.rule_form_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onDI() {
        super.onDI()
        DaggerRuleFormComponent.builder()
            .fragmentComponent(fragmentComponent)
            .build()
            .also { it.inject(this) }
    }

}
