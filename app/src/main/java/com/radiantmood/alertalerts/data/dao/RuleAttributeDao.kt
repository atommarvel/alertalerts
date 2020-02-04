package com.radiantmood.alertalerts.data.dao

import androidx.room.*
import com.radiantmood.alertalerts.data.entity.RuleAttribute

@Dao
interface RuleAttributeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRuleAttrs(vararg ruleAttrs: RuleAttribute)

    @Update
    fun updateRuleAttrs(vararg ruleAttrs: RuleAttribute)

    @Delete
    fun deleteRuleAttrs(vararg ruleAttrs: RuleAttribute)

    @Query("SELECT * FROM RULE_ATTR WHERE ruleId = :rule")
    fun loadRuleAttrs(rule: Int): List<RuleAttribute>
}