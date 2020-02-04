package com.radiantmood.alertalerts.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.radiantmood.alertalerts.data.dao.RuleAttributeDao
import com.radiantmood.alertalerts.data.dao.RuleDao
import com.radiantmood.alertalerts.data.entity.Rule
import com.radiantmood.alertalerts.data.entity.RuleAttribute


@Database(entities = [Rule::class, RuleAttribute::class], version = 1)
abstract class RuleDatabase : RoomDatabase() {
    abstract fun ruleDao(): RuleDao
    abstract fun ruleAttrDao(): RuleAttributeDao
}

fun getRuleDb(context: Context) = Room.databaseBuilder(
    context.applicationContext,
    RuleDatabase::class.java, "alertalerts.db"
).build()
