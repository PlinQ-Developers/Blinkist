package com.blinkslabs.blinkist.android.challenge.ui.books.bookItem

import com.blinkslabs.blinkist.android.challenge.domain.models.Book
import com.blinkslabs.blinkist.android.challenge.utils.UiEffect
import com.blinkslabs.blinkist.android.challenge.utils.UiEvent
import com.blinkslabs.blinkist.android.challenge.utils.UiState

class BookItemFragmentContract {
    data class State(
        val bookItem: Book?,
    ) : UiState

    sealed class Effect : UiEffect {
        object NavigateToBookListFragment : Effect()
        data class ShowErrorMessage(
            val message: String,
        ) : Effect()
    }

    sealed class Event : UiEvent {
        object OnUserClickBackIcon : Event()
    }
}
