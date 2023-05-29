package com.blinkslabs.blinkist.android.challenge.ui.books.bookList

import com.blinkslabs.blinkist.android.challenge.domain.models.Book
import com.blinkslabs.blinkist.android.challenge.utils.SortOrder
import com.blinkslabs.blinkist.android.challenge.utils.UiEffect
import com.blinkslabs.blinkist.android.challenge.utils.UiEvent
import com.blinkslabs.blinkist.android.challenge.utils.UiState

class BookListFragmentContract {
    data class State(
        val isLoading: Boolean,
        val bookList: List<Book>,
        val errorMessage: String?,
        val filterOrder: SortOrder,
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShowErrorMessage(
            val message: String,
        ) : Effect()

        data class NavigateToBookItem(
            val bookId: String,
        ) : Effect()
        object ShowSortFilterMenu : Effect()
    }

    sealed class Event : UiEvent {
        data class OnUserClickBookItem(
            val bookId: String,
        ) : Event()
        data class OnUserSortBookList(
            val order: SortOrder,
        ) : Event()
        object OnUserRefreshFeed : Event()
        object OnUserClickSortIcon : Event()
    }
}
