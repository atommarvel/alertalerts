package com.radiantmood.alertalerts.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radiantmood.alertalerts.repo.RulesRepo
import javax.inject.Inject

class MainViewModel
@Inject constructor(private val rulesRepo: RulesRepo) : ViewModel() {

    val mainModelLiveData: MutableLiveData<MainModel> = MutableLiveData()

    fun getRules() {
        mainModelLiveData.value = MainModel(true, rulesRepo.getRules())
    }
}
