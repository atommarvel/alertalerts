package com.radiantmood.alertalerts.ui.ruleform

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.airbnb.epoxy.TypedEpoxyController
import com.radiantmood.alertalerts.formEditText
import com.radiantmood.alertalerts.ui.common.watch

data class RuleFormModel(val ruleName: FormEditTextModel)

data class FormEditTextModel(val id: Int, val title: String, val hint: String, val input: String = "")

class RuleFormEController(private val viewModel: RuleFormViewModel) : TypedEpoxyController<RuleFormModel>() {

    fun observeForRuleFormModel(lifecycleOwner: LifecycleOwner, liveData: LiveData<RuleFormModel>) {
        // TODO: consider having a separate live data for edit texts to reduce the amount of calls made
        liveData.observe(lifecycleOwner, Observer {
            setData(it)
        })
    }

    override fun buildModels(data: RuleFormModel) {
        val ruleName = data.ruleName
        formEditText {
            id(ruleName.id)
            title(ruleName.title)
            hint(ruleName.hint)
            input(ruleName.input)
            watch(viewModel.titleTextWatcher)
        }
        // rule enabled/disabled SwitchCompat

        // add app filter button
        // current app filters with deletion

        // triggers edit text & add btn
        // current triggers with deletion

        // suppressors edit text & add btn
        // current suppress with deletion
    }
}

