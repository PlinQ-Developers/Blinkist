package com.blinkslabs.blinkist.android.challenge.domain.interactors

import com.blinkslabs.blinkist.android.challenge.domain.repositories.BooksRepository
import javax.inject.Inject

class SetBookmarkStatusUseCase @Inject constructor(
    private val repository: BooksRepository,
) {
    suspend operator fun invoke(
        bookmark: Boolean,
        bookId: String,
    ) = repository.setBookmarkedStatus(
        bookmarkStatus = bookmark,
        bookId = bookId,
    )
}
