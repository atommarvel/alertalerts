package com.radiantmood.alertalerts.di

import android.content.Context
import androidx.room.Room
import com.radiantmood.alertalerts.data.database.RuleDatabase
import dagger.Module
import dagger.Provides


@Module
class DatabaseModule {
    @Provides
    fun database(context: Context): RuleDatabase = Room.databaseBuilder(
        context.applicationContext,
        RuleDatabase::class.java,
        "alertalerts.db"
    ).build()
}