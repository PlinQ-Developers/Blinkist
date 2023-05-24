package com.blinkslabs.blinkist.android.challenge.ui.books.bookList

import com.blinkslabs.blinkist.android.challenge.domain.models.Book
import com.blinkslabs.blinkist.android.challenge.utils.UiEffect
import com.blinkslabs.blinkist.android.challenge.utils.UiEvent
import com.blinkslabs.blinkist.android.challenge.utils.UiState

class BookListFragmentContract {
    data class State(
        val isLoading: Boolean,
        val bookList: List<Book?>,
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShowErrorMessage(
            val message: String,
        ) : Effect()

        data class NavigateToBookItem(
            val bookId: String,
        ) : Effect()
    }

    sealed class Event : UiEvent {
        data class OnUserClickBookItem(
            val bookId: String,
        ) : Event()
    }
}
