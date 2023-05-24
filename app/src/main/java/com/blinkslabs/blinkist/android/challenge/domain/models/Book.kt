package com.blinkslabs.blinkist.android.challenge.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "books_table",
)
data class Book(
    @PrimaryKey
    val id: String,
    val name: String,
    val author: String,
    val publishDate: String,
    val coverImageUrl: String,
)
