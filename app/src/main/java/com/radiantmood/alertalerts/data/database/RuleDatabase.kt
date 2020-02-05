package com.radiantmood.alertalerts.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.radiantmood.alertalerts.data.dao.RuleAttributeDao
import com.radiantmood.alertalerts.data.dao.RuleDao
import com.radiantmood.alertalerts.data.entity.Rule
import com.radiantmood.alertalerts.data.entity.RuleAttrType
import com.radiantmood.alertalerts.data.entity.RuleAttribute


@Database(entities = [Rule::class, RuleAttribute::class], version = 1)
@TypeConverters(Converters::class)
abstract class RuleDatabase : RoomDatabase() {
    abstract fun ruleDao(): RuleDao
    abstract fun ruleAttrDao(): RuleAttributeDao
}

class Converters {
    @TypeConverter
    fun ruleAttributeToTnt(value: RuleAttrType) = value.toInt()

    @TypeConverter
    fun intToRuleAttribute(value: Int) = value.toEnum<RuleAttrType>()
}

@Suppress("NOTHING_TO_INLINE")
private inline fun <T : Enum<T>> T.toInt(): Int = this.ordinal

private inline fun <reified T : Enum<T>> Int.toEnum(): T = enumValues<T>()[this]