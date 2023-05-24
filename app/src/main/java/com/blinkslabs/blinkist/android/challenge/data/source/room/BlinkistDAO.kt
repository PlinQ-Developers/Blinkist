package com.blinkslabs.blinkist.android.challenge.data.source.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blinkslabs.blinkist.android.challenge.domain.models.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BlinkistDAO {

    @Query("SELECT * FROM books_table")
    fun getBooksList(): Flow<List<Book>>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE,
    )
    suspend fun updateBookList(
        bookList: List<Book>,
    )
}
