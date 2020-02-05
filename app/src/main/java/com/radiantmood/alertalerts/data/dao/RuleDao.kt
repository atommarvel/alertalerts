package com.radiantmood.alertalerts.data.dao

import androidx.room.*
import com.radiantmood.alertalerts.data.entity.Rule

@Dao
interface RuleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRules(vararg rules: Rule)

    @Update
    suspend fun updateRules(vararg rules: Rule)

    @Delete
    suspend fun deleteRules(vararg rules: Rule)

    @Query("SELECT * FROM rule ORDER BY ENABLED, LAST_UPDATED DESC")
    suspend fun loadAllRules(): List<Rule>
}