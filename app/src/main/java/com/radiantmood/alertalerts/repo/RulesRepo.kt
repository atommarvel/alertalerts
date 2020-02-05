package com.radiantmood.alertalerts.repo

import com.radiantmood.alertalerts.data.database.RuleDatabase
import com.radiantmood.alertalerts.data.entity.Rule
import javax.inject.Inject

class RulesRepo @Inject constructor(private val ruleDb: RuleDatabase) {
    suspend fun getRules() = ruleDb.ruleDao().loadAllRules()
    suspend fun addRules(vararg rules: Rule) = ruleDb.ruleDao().insertRules(*rules)
}