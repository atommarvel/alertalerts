package com.radiantmood.alertalerts.repo

import com.radiantmood.alertalerts.data.database.RuleDatabase
import com.radiantmood.alertalerts.data.entity.Rule
import java.util.*
import javax.inject.Inject

class RulesRepo @Inject constructor(private val ruleDb: RuleDatabase) {
    suspend fun getRules(): List<Rule> {
        val rules = ruleDb.ruleDao().loadAllRules()
        if (rules.isEmpty()) {
            addExampleRules()
        }
        return ruleDb.ruleDao().loadAllRules()
    }

    suspend fun addRules(vararg rules: Rule) = ruleDb.ruleDao().insertRules(*rules)

    suspend fun addExampleRules() {
        val rules = (0..10).map { i ->
            val now = Calendar.getInstance().timeInMillis
            Rule(i, now, "Example item #$i", i % 2 == 0)
        }.toTypedArray()
        addRules(*rules)
    }
}