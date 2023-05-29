package com.blinkslabs.blinkist.android.challenge.ui.books.bookList

import androidx.lifecycle.viewModelScope
import com.blinkslabs.blinkist.android.challenge.domain.interactors.FetchNetworkBookListUseCase
import com.blinkslabs.blinkist.android.challenge.domain.interactors.GetSavedBooksListUseCase
import com.blinkslabs.blinkist.android.challenge.utils.BaseViewModel
import com.blinkslabs.blinkist.android.challenge.utils.Constants
import com.blinkslabs.blinkist.android.challenge.utils.Resource
import com.blinkslabs.blinkist.android.challenge.utils.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListFragmentViewModel @Inject constructor(
    private val getSavedBooksListUseCase: GetSavedBooksListUseCase,
    private val fetchNetworkBookListUseCase: FetchNetworkBookListUseCase,
) : BaseViewModel<BookListFragmentContract.Event, BookListFragmentContract.State, BookListFragmentContract.Effect>() {

    override fun createInitialState(): BookListFragmentContract.State {
        return BookListFragmentContract.State(
            isLoading = false,
            bookList = emptyList(),
            errorMessage = null,
            filterOrder = SortOrder.BY_NAME,
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

            is BookListFragmentContract.Event.OnUserRefreshFeed -> {
                setState { copy(isLoading = true) }
                loadNetworkBookList()
            }

            is BookListFragmentContract.Event.OnUserClickSortIcon -> {
                setEffect {
                    BookListFragmentContract.Effect.ShowSortFilterMenu
                }
            }

            is BookListFragmentContract.Event.OnUserSortBookList -> {
                setState { copy(filterOrder = event.order) }
                getSavedBookList(order = event.order)
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
            getSavedBookList(order = SortOrder.BY_NAME)
        }
    }

    private fun getSavedBookList(order: SortOrder) {
        getSavedBooksListUseCase(order).onEach { bookListFlow ->
            bookListFlow.collect { bookList ->
                setState {
                    copy(
                        bookList = bookList,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadNetworkBookList() {
        fetchNetworkBookListUseCase().onEach { resource: Resource<Boolean> ->
            when (resource) {
                is Resource.Loading -> {
                    setState { copy(isLoading = true) }
                }

                is Resource.Success -> {
                    setState { copy(isLoading = false) }
                }

                is Resource.Error -> {
                    setState {
                        copy(
                            isLoading = false,
                            errorMessage = resource.message ?: "An unexpected error occurred!",
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}
