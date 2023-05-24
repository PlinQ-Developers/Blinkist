package com.blinkslabs.blinkist.android.challenge.data.repositories

import com.blinkslabs.blinkist.android.challenge.data.source.remote.api.MockBooksApi
import com.blinkslabs.blinkist.android.challenge.data.source.remote.dto.BookDTO
import com.blinkslabs.blinkist.android.challenge.data.source.remote.dto.toBook
import com.blinkslabs.blinkist.android.challenge.data.source.room.BlinkistDAO
import com.blinkslabs.blinkist.android.challenge.domain.models.Book
import com.blinkslabs.blinkist.android.challenge.domain.repositories.BooksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val databaseDAO: BlinkistDAO,
    private val mockBooksApi: MockBooksApi,
) : BooksRepository {

    override fun savedBookList(): Flow<List<Book>> {
        return databaseDAO.getBooksList()
    }

    override suspend fun updateBooksList() {
        val booksList = mockBooksApi.getAllBooks().map { bookDTO: BookDTO ->
            bookDTO.toBook()
        }
        databaseDAO.updateBookList(
            bookList = booksList,
        )
    }
}
