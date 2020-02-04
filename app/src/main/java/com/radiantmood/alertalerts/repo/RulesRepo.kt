package com.radiantmood.alertalerts.repo

import com.radiantmood.alertalerts.data.entity.Rule
import javax.inject.Inject

class RulesRepo @Inject constructor() {
    fun getRules() = listOf(Rule(0, 0, "first rule", true))
}