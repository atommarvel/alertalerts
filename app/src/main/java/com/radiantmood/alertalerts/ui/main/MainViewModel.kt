package com.radiantmood.alertalerts.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radiantmood.alertalerts.BuildConfig
import com.radiantmood.alertalerts.data.entity.Rule
import com.radiantmood.alertalerts.data.entity.RuleAttrType
import com.radiantmood.alertalerts.data.entity.RuleAttribute
import com.radiantmood.alertalerts.repo.NotifListenerPermissionRepo
import com.radiantmood.alertalerts.repo.RulesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val rulesRepo: RulesRepo,
    private val notifListenerPermissionRepo: NotifListenerPermissionRepo
) : ViewModel() {

    val mainModelLiveData: MutableLiveData<MainModel> = MutableLiveData()

    private var currSnifferState = !notifListenerPermissionRepo.isNotifListenerPermissionEnabled()
    private var rules = listOf<Rule>()

    private val mainModel get() = MainModel(currSnifferState, "Your Rules", rules)

    fun getData() = viewModelScope.launch(Dispatchers.IO) {
        updateRules()
        updateSnifferPrompt()
        mainModelLiveData.postValue(mainModel)
    }

    fun checkNotifListenerPermission() {
        updateSnifferPrompt()
        mainModelLiveData.value = mainModel
    }

    private suspend fun updateRules() {
        rules = rulesRepo.getRules().also {
            if (it.isEmpty() && BuildConfig.DEBUG) {
                setupDebugRules()
            }
        }
    }

    private fun updateSnifferPrompt() {
        currSnifferState = !notifListenerPermissionRepo.isNotifListenerPermissionEnabled()
    }

    private suspend fun setupDebugRules() {
        val now = Calendar.getInstance().timeInMillis
        val rules = listOf(
            Rule(0, now, "Emilia", true),
            Rule(1, now, "Emilia Messenger", true)
        )
        val ruleAttributes = listOf(
            RuleAttribute(0, 0, RuleAttrType.TRIGGER, "emilia"),
            RuleAttribute(1, 0, RuleAttrType.TRIGGER, "poppe"),
            RuleAttribute(2, 0, RuleAttrType.SUPPRESS, "silent"),

            // 'com.facebook.orca' is messenger
            RuleAttribute(3, 1, RuleAttrType.APP_FILTER, "com.facebook.orca"),
            RuleAttribute(4, 1, RuleAttrType.TRIGGER, "dove-y"),
            RuleAttribute(5, 1, RuleAttrType.SUPPRESS, "silent")
        )

        rulesRepo.addRules(*rules.toTypedArray())
        rulesRepo.addRuleAttributes(*ruleAttributes.toTypedArray())
    }
}
