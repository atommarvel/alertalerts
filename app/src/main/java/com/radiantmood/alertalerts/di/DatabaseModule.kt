package com.radiantmood.alertalerts.di

import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import com.radiantmood.alertalerts.data.database.RuleDatabase
import dagger.Module
import dagger.Provides


@Module
class DatabaseModule {
    @Provides
    fun database(context: FragmentActivity): RuleDatabase = Room.databaseBuilder(
        context.applicationContext,
        RuleDatabase::class.java,
        "alertalerts.db"
    ).build()
}