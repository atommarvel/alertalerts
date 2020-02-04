package com.radiantmood.alertalerts.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radiantmood.alertalerts.data.entity.Rule
import com.radiantmood.alertalerts.repo.RulesRepo
import javax.inject.Inject

class MainViewModel
@Inject constructor(private val rulesRepo: RulesRepo) : ViewModel() {

    val rulesLiveData: MutableLiveData<List<Rule>> = MutableLiveData()

    fun getRules() {
        rulesLiveData.value = rulesRepo.getRules()
    }
}
