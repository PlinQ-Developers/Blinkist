package com.blinkslabs.blinkist.android.challenge.di

import com.blinkslabs.blinkist.android.challenge.domain.interactors.FetchNetworkBookListUseCase
import com.blinkslabs.blinkist.android.challenge.domain.interactors.GetBookItemByIdUseCase
import com.blinkslabs.blinkist.android.challenge.domain.interactors.GetSavedBooksListUseCase
import com.blinkslabs.blinkist.android.challenge.domain.repositories.BooksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorsComponent {

    @Provides
    @Singleton
    fun provideGetSavedBooksListUseCase(
        repository: BooksRepository,
    ): GetSavedBooksListUseCase {
        return GetSavedBooksListUseCase(
            repository = repository,
        )
    }

    @Provides
    @Singleton
    fun provideFetchNetworkBookListUseCase(
        repository: BooksRepository,
    ): FetchNetworkBookListUseCase {
        return FetchNetworkBookListUseCase(
            booksRepository = repository,
        )
    }

    @Provides
    @Singleton
    fun provideGetBookItemsByIdUseCase(
        repository: BooksRepository,
    ): GetBookItemByIdUseCase {
        return GetBookItemByIdUseCase(
            repository = repository,
        )
    }
}
