package com.blinkslabs.blinkist.android.challenge.data.source.remote.dto

import com.blinkslabs.blinkist.android.challenge.domain.models.Book
import org.threeten.bp.LocalDate

data class BookDTO(
    val id: String,
    val name: String,
    val author: String,
    val publishDate: LocalDate,
    val coverImageUrl: String,
    val description: String,
    val rating: Double,
    val category: String,
    val pages: Int,
    val estimatedReadTime: String,
)

fun BookDTO.toBook(): Book {
    return Book(
        id = id,
        name = name,
        author = author,
        publishDate = publishDate.toString(),
        coverImageUrl = coverImageUrl,
        description = description,
        rating = rating,
        category = category,
        pages = pages,
        estimatedReadTime = estimatedReadTime,
    )
}
