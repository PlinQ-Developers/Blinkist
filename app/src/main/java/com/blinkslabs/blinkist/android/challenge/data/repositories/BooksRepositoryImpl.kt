package com.blinkslabs.blinkist.android.challenge.data.repositories

import com.blinkslabs.blinkist.android.challenge.data.source.remote.api.MockBooksApi
import com.blinkslabs.blinkist.android.challenge.data.source.remote.dto.BookDTO
import com.blinkslabs.blinkist.android.challenge.data.source.remote.dto.toBook
import com.blinkslabs.blinkist.android.challenge.data.source.room.BlinkistDAO
import com.blinkslabs.blinkist.android.challenge.domain.models.Book
import com.blinkslabs.blinkist.android.challenge.domain.repositories.BooksRepository
import com.blinkslabs.blinkist.android.challenge.utils.SortOrder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val databaseDAO: BlinkistDAO,
    private val mockBooksApi: MockBooksApi,
) : BooksRepository {

    override fun getBooksList(sortOrder: SortOrder): Flow<List<Book>> {
        return databaseDAO.getBooks(
            sortOrder = sortOrder,
        )
    }

    override suspend fun updateBooksList(): Boolean {
        val booksList = mockBooksApi.getAllBooks().map { bookDTO: BookDTO ->
            bookDTO.toBook()
        }
        databaseDAO.updateBookList(
            bookList = booksList,
        )
        return true
    }

    override fun getBoonItemById(bookId: String): Flow<Book> {
        return databaseDAO.getBookByItemId(
            bookId = bookId,
        )
    }
}
