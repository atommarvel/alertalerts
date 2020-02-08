package com.radiantmood.alertalerts.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radiantmood.alertalerts.data.entity.Rule
import com.radiantmood.alertalerts.repo.NotifListenerPermissionRepo
import com.radiantmood.alertalerts.repo.RulesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val rulesRepo: RulesRepo,
    private val notifListenerPermissionRepo: NotifListenerPermissionRepo
) : ViewModel() {

    val mainModelLiveData: MutableLiveData<MainModel> = MutableLiveData()

    private var currSnifferState = !notifListenerPermissionRepo.isNotifListenerPermissionEnabled()
    private var rules = listOf<Rule>()

    private val mainModel get() = MainModel(currSnifferState, rules)

    fun getData() = viewModelScope.launch(Dispatchers.IO) {
        updateRules()
        updateSnifferPrompt()
        mainModelLiveData.postValue(mainModel)
    }

    private suspend fun updateRules() {
        rules = rulesRepo.getRules().also {
            if (it.isEmpty()) {
                rulesRepo.addExampleRules()
            }
        }
    }

    private fun updateSnifferPrompt() {
        currSnifferState = !notifListenerPermissionRepo.isNotifListenerPermissionEnabled()
    }

    fun checkNotifListenerPermission() {
        updateSnifferPrompt()
        mainModelLiveData.value = mainModel
    }
}
