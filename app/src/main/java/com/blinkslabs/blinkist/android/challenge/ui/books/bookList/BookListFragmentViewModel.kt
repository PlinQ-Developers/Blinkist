package com.blinkslabs.blinkist.android.challenge.ui.books.bookList

import androidx.lifecycle.viewModelScope
import com.blinkslabs.blinkist.android.challenge.domain.interactors.GetSavedBooksListUseCase
import com.blinkslabs.blinkist.android.challenge.utils.BaseViewModel
import com.blinkslabs.blinkist.android.challenge.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListFragmentViewModel @Inject constructor(
    private val getSavedBooksListUseCase: GetSavedBooksListUseCase,
) : BaseViewModel<BookListFragmentContract.Event, BookListFragmentContract.State, BookListFragmentContract.Effect>() {

    override fun createInitialState(): BookListFragmentContract.State {
        return BookListFragmentContract.State(
            isLoading = false,
            bookList = emptyList(),
        )
    }

    override fun handleEvent(event: BookListFragmentContract.Event) {
        when (event) {
            is BookListFragmentContract.Event.OnUserClickBookItem -> {
                setEffect {
                    BookListFragmentContract.Effect.NavigateToBookItem(
                        bookId = event.bookId,
                    )
                }
            }
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        setEffect {
            BookListFragmentContract.Effect.ShowErrorMessage(
                message = throwable.message ?: Constants.ERROR_MESSAGE,
            )
        }
    }

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            getSavedBookList()
        }
    }

    private fun getSavedBookList() {
        getSavedBooksListUseCase().onEach { bookListFlow ->
            bookListFlow.collect { bookList ->
                setState {
                    copy(
                        bookList = bookList,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
