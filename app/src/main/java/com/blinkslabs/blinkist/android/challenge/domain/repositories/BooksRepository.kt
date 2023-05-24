package com.blinkslabs.blinkist.android.challenge.domain.repositories

import com.blinkslabs.blinkist.android.challenge.domain.models.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun savedBookList(): Flow<List<Book>>

    suspend fun updateBooksList()
}
