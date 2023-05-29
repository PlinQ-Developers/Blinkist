package com.blinkslabs.blinkist.android.challenge.data

import com.blinkslabs.blinkist.android.challenge.data.source.remote.api.BooksApi
import com.blinkslabs.blinkist.android.challenge.data.source.remote.dto.BookDTO
import com.blinkslabs.blinkist.android.challenge.domain.interactors.FetchNetworkBookListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class BooksServiceShould {

    @Mock
    lateinit var booksApi: BooksApi

    @InjectMocks
    lateinit var fetchNetworkBookListUseCase: FetchNetworkBookListUseCase

    private val mockBooks: List<BookDTO> = listOf(
        mock(),
        mock(),
        mock(),
    )
}
