package com.radiantmood.alertalerts.data.dao

import androidx.room.*
import com.radiantmood.alertalerts.data.entity.RuleAttribute

@Dao
interface RuleAttributeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRuleAttrs(vararg ruleAttrs: RuleAttribute)

    @Update
    suspend fun updateRuleAttrs(vararg ruleAttrs: RuleAttribute)

    @Delete
    suspend fun deleteRuleAttrs(vararg ruleAttrs: RuleAttribute)

    @Query("SELECT * FROM RULE_ATTR WHERE ruleId = :rule")
    suspend fun loadRuleAttrs(rule: Int): List<RuleAttribute>
}