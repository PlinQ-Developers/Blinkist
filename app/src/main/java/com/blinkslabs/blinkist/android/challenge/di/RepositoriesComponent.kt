package com.blinkslabs.blinkist.android.challenge.di

import com.blinkslabs.blinkist.android.challenge.data.repositories.BooksRepositoryImpl
import com.blinkslabs.blinkist.android.challenge.data.source.remote.api.MockBooksApi
import com.blinkslabs.blinkist.android.challenge.data.source.room.BlinkistDAO
import com.blinkslabs.blinkist.android.challenge.domain.repositories.BooksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesComponent {

    @Provides
    @Singleton
    fun provideBooksRepository(
        dao: BlinkistDAO,
        api: MockBooksApi,
    ): BooksRepository {
        return BooksRepositoryImpl(
            databaseDAO = dao,
            mockBooksApi = api,
        )
    }
}
