package com.blinkslabs.blinkist.android.challenge.domain.interactors

import com.blinkslabs.blinkist.android.challenge.domain.repositories.BooksRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBookItemByIdUseCase @Inject constructor(
    private val repository: BooksRepository,
) {
    operator fun invoke(bookId: String) = flow {
        emit(
            repository.getBoonItemById(
                bookId = bookId,
            ),
        )
    }
}
