package com.radiantmood.alertalerts.data.dao

import androidx.room.*
import com.radiantmood.alertalerts.data.entity.Rule

@Dao
interface RuleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRules(vararg rules: Rule)

    @Update
    fun updateRules(vararg rules: Rule)

    @Delete
    fun deleteRules(vararg rules: Rule)

    @Query("SELECT * FROM rule ORDER BY ENABLED, LAST_UPDATED DESC")
    fun loadAllRules(): List<Rule>
}