package com.radiantmood.alertalerts.ui.ruleform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.radiantmood.alertalerts.R
import com.radiantmood.alertalerts.core.BaseFragment
import com.radiantmood.alertalerts.di.DaggerRuleFormComponent
import kotlinx.android.synthetic.main.rule_form_fragment.*

class RuleFormFragment : BaseFragment() {

    private val viewModel: RuleFormViewModel by lazy { getViewModel(RuleFormViewModel::class.java) }
    // TODO: inject viewmodel
    private val controller: RuleFormEController by lazy { RuleFormEController(viewModel) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.rule_form_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        form_rv.setController(controller)
        controller.observeForRuleFormModel(this, viewModel.ruleFormLiveData)
        viewModel.getData()
    }

    override fun onDI() {
        super.onDI()
        DaggerRuleFormComponent.builder()
            .fragmentComponent(fragmentComponent)
            .build()
            .also { it.inject(this) }
    }

}
