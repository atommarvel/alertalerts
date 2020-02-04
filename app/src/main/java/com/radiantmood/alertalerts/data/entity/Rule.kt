package com.radiantmood.alertalerts.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rule(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "LAST_UPDATED") val lastUpdated: Long,
    val name: String,
    val enabled: Boolean
)