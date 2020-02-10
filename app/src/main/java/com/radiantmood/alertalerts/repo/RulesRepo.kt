package com.radiantmood.alertalerts.repo

import com.radiantmood.alertalerts.data.database.RuleDatabase
import com.radiantmood.alertalerts.data.entity.Rule
import com.radiantmood.alertalerts.data.entity.RuleAttribute
import javax.inject.Inject

class RulesRepo @Inject constructor(private val ruleDb: RuleDatabase) {
    suspend fun getRules(): List<Rule> = ruleDb.ruleDao().loadAllRules()

    suspend fun addRules(vararg rules: Rule) = ruleDb.ruleDao().insertRules(*rules)

    suspend fun getEnabledRules(): List<Rule> = ruleDb.ruleDao().loadEnabledRules()

    suspend fun addRuleAttributes(vararg ruleAttributes: RuleAttribute) =
        ruleDb.ruleAttrDao().insertRuleAttrs(*ruleAttributes)

    suspend fun getAttributes(rule: Rule) = ruleDb.ruleAttrDao().loadRuleAttrs(rule.id)
}