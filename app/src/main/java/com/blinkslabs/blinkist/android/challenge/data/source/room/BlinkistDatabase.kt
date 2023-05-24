package com.blinkslabs.blinkist.android.challenge.data.source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blinkslabs.blinkist.android.challenge.data.source.room.BlinkistDatabase.Companion.DATABASE_VERSION
import com.blinkslabs.blinkist.android.challenge.domain.models.Book

@Database(
    entities = [
        Book::class,
    ],
    version = DATABASE_VERSION,
    exportSchema = true,
    autoMigrations = [],
)
abstract class BlinkistDatabase : RoomDatabase() {
    abstract val databaseDAO: BlinkistDAO

    companion object {
        const val DATABASE_VERSION = 1
    }
}
