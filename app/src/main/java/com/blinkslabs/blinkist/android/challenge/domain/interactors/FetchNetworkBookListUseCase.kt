package com.blinkslabs.blinkist.android.challenge.domain.interactors

import com.blinkslabs.blinkist.android.challenge.domain.repositories.BooksRepository
import javax.inject.Inject

class FetchNetworkBookListUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
) {
    suspend operator fun invoke() {
        booksRepository.updateBooksList()
    }
}
