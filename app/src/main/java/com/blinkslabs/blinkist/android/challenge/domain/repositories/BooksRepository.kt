package com.blinkslabs.blinkist.android.challenge.domain.repositories

import com.blinkslabs.blinkist.android.challenge.domain.models.Book
import com.blinkslabs.blinkist.android.challenge.utils.SortOrder
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun getBooksList(sortOrder: SortOrder): Flow<List<Book>>

    fun getBoonItemById(bookId: String): Flow<Book>

    suspend fun setBookmarkedStatus(
        bookmarkStatus: Boolean,
        bookId: String,
    )

    suspend fun updateBooksList(): Boolean
}
