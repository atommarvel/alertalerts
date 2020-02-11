package com.radiantmood.alertalerts.util

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.reflect.KProperty

class LongPrefDelegate(private val key: String, private val defValue: Long, private val prefs: SharedPreferences) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Long {
        return prefs.getLong(key, defValue)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
        prefs.edit {
            putLong(key, value)
        }
    }
}

class BooleanPrefDelegate(
    private val key: String,
    private val defValue: Boolean,
    private val prefs: SharedPreferences
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return prefs.getBoolean(key, defValue)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        prefs.edit {
            putBoolean(key, value)
        }
    }
}

class IntPrefDelegate(private val key: String, private val defValue: Int, private val prefs: SharedPreferences) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return prefs.getInt(key, defValue)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        prefs.edit {
            putInt(key, value)
        }
    }
}