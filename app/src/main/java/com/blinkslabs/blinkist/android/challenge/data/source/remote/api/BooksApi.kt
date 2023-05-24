package com.blinkslabs.blinkist.android.challenge.data.source.remote.api

import com.blinkslabs.blinkist.android.challenge.data.source.remote.dto.BookDTO

interface BooksApi {
    suspend fun getAllBooks(): List<BookDTO>
}
