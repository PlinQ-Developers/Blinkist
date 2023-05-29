package com.blinkslabs.blinkist.android.challenge.domain.interactors

import com.blinkslabs.blinkist.android.challenge.domain.repositories.BooksRepository
import com.blinkslabs.blinkist.android.challenge.utils.SortOrder
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSavedBooksListUseCase @Inject constructor(
    private val repository: BooksRepository,
) {
    operator fun invoke(
        order: SortOrder,
    ) = flow {
        val booksList = repository.getBooksList(
            sortOrder = order,
        )
        emit(booksList)
    }
}
