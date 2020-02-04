package com.radiantmood.alertalerts.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RULE_ATTR")
data class RuleAttribute(
    @PrimaryKey val id: Int,
    val ruleId: Int,
    val type: RuleAttrType,
    val target: String
)