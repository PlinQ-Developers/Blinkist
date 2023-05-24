package com.blinkslabs.blinkist.android.challenge.domain.interactors

import com.blinkslabs.blinkist.android.challenge.domain.repositories.BooksRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSavedBooksListUseCase @Inject constructor(
    private val repository: BooksRepository,
) {
    operator fun invoke() = flow {
        val booksList = repository.savedBookList()
        emit(booksList)
    }
}
