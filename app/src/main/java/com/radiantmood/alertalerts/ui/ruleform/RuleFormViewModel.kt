package com.radiantmood.alertalerts.ui.ruleform

import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radiantmood.alertalerts.util.onTextChange
import javax.inject.Inject

class RuleFormViewModel @Inject constructor() : ViewModel() {
    val titleTextWatcher: TextWatcher by lazy { onTextChange { updateInput(it) } }

    private lateinit var ruleFormModel: RuleFormModel

    val ruleFormLiveData: MutableLiveData<RuleFormModel> = MutableLiveData()

    fun getData() {
        val ruleName = FormEditTextModel(0, "Rule Name", "Enter Rule Name Here")
        ruleFormModel = RuleFormModel(ruleName)
        ruleFormLiveData.value = ruleFormModel
    }

    private fun updateInput(input: String) {
        val ruleName = ruleFormModel.ruleName.copy(input = input)
        ruleFormModel = ruleFormModel.copy(ruleName = ruleName)
        ruleFormLiveData.value = ruleFormModel
    }


}
