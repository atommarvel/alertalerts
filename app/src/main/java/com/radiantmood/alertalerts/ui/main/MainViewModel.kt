package com.radiantmood.alertalerts.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radiantmood.alertalerts.data.entity.Rule
import com.radiantmood.alertalerts.repo.RulesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainViewModel
@Inject constructor(private val rulesRepo: RulesRepo) : ViewModel() {

    val mainModelLiveData: MutableLiveData<MainModel> = MutableLiveData()

    fun getRules() = viewModelScope.launch(Dispatchers.IO) {
        val rules = rulesRepo.getRules()
        if (rules.isEmpty()) {
            addHardCodedRules()
        }
        mainModelLiveData.postValue(MainModel(true, rules))
    }

    private suspend fun addHardCodedRules() {
        val rules = (0..10).map { i ->
            val now = Calendar.getInstance().timeInMillis
            Rule(i, now, "Example item #$i", i % 2 == 0)
        }.toTypedArray()
        rulesRepo.addRules(*rules)
    }
}
