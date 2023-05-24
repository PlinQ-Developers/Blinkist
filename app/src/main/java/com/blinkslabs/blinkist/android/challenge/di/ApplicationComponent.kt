package com.blinkslabs.blinkist.android.challenge.di

import android.app.Application
import androidx.room.Room
import com.blinkslabs.blinkist.android.challenge.data.source.remote.api.MockBooksApi
import com.blinkslabs.blinkist.android.challenge.data.source.room.BlinkistDAO
import com.blinkslabs.blinkist.android.challenge.data.source.room.BlinkistDatabase
import com.blinkslabs.blinkist.android.challenge.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationComponent {

    @Provides
    @Singleton
    fun provideMockBooksApi(): MockBooksApi {
        return MockBooksApi()
    }

    @Provides
    @Singleton
    fun provideBlinkistDatabase(
        application: Application,
    ): BlinkistDatabase {
        return Room.databaseBuilder(
            application,
            BlinkistDatabase::class.java,
            Constants.DATABASE_NAME,
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideBlinkistDAO(
        database: BlinkistDatabase,
    ): BlinkistDAO {
        return database.databaseDAO
    }
}
