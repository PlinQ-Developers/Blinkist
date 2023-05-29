package com.blinkslabs.blinkist.android.challenge.ui.books.bookItem

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.blinkslabs.blinkist.android.challenge.domain.interactors.GetBookItemByIdUseCase
import com.blinkslabs.blinkist.android.challenge.domain.interactors.SetBookmarkStatusUseCase
import com.blinkslabs.blinkist.android.challenge.domain.models.Book
import com.blinkslabs.blinkist.android.challenge.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookItemFragmentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBookItemByIdUseCase: GetBookItemByIdUseCase,
    private val setBookmarkStatusUseCase: SetBookmarkStatusUseCase,
) : BaseViewModel<BookItemFragmentContract.Event, BookItemFragmentContract.State, BookItemFragmentContract.Effect>() {
    private val selectedBookId: String? = savedStateHandle["bookItemId"]

    override fun createInitialState(): BookItemFragmentContract.State {
        return BookItemFragmentContract.State(
            bookItem = null,
        )
    }

    override fun handleEvent(event: BookItemFragmentContract.Event) {
        when (event) {
            is BookItemFragmentContract.Event.OnUserClickBackIcon -> {
                setEffect {
                    BookItemFragmentContract.Effect.NavigateToBookListFragment
                }
            }

            is BookItemFragmentContract.Event.OnUserClickBookmarkIcon -> {
                setBookmarked(
                    bookmarkStatus = event.bookmarkStatus,
                    bookId = event.bookId,
                )
            }
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        setEffect {
            BookItemFragmentContract.Effect.ShowErrorMessage(
                message = throwable.message ?: "An unexpected error occurred!",
            )
        }
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            getBookItem()
        }
    }

    private fun getBookItem() {
        val bookId =
            selectedBookId ?: throw IllegalArgumentException("An unexpected error occurred!")
        getBookItemByIdUseCase(bookId = bookId).onEach { bookFlow: Flow<Book> ->
            bookFlow.collect { book: Book ->
                setState { copy(bookItem = book) }
            }
        }.launchIn(viewModelScope)
    }

    private fun setBookmarked(
        bookmarkStatus: Boolean,
        bookId: String,
    ) {
        viewModelScope.launch {
            setBookmarkStatusUseCase(
                bookmark = bookmarkStatus,
                bookId = bookId,
            )
        }
    }
}
