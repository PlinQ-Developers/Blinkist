package com.blinkslabs.blinkist.android.challenge.data.source.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.blinkslabs.blinkist.android.challenge.data.source.room.BlinkistDatabase.Companion.DATABASE_VERSION
import com.blinkslabs.blinkist.android.challenge.data.source.room.BlinkistDatabase.Companion.DATABASE_VERSION_V1
import com.blinkslabs.blinkist.android.challenge.domain.models.Book

@Database(
    entities = [
        Book::class,
    ],
    version = DATABASE_VERSION,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = DATABASE_VERSION_V1, to = DATABASE_VERSION),
    ],
)
abstract class BlinkistDatabase : RoomDatabase() {
    abstract val databaseDAO: BlinkistDAO

    companion object {
        const val DATABASE_VERSION = 2
        const val DATABASE_VERSION_V1 = 1
    }
}
