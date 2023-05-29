package com.blinkslabs.blinkist.android.challenge.data.source.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blinkslabs.blinkist.android.challenge.domain.models.Book
import com.blinkslabs.blinkist.android.challenge.utils.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface BlinkistDAO {

    fun getBooks(
        sortOrder: SortOrder,
    ): Flow<List<Book>> =
        when (sortOrder) {
            SortOrder.BY_DATE -> getBooksSortedByDatePublished()
            SortOrder.BY_NAME -> getBooksSortedByNameOrderAlphabetically()
        }

    @Query("SELECT * FROM books_table ORDER BY name ASC")
    fun getBooksSortedByNameOrderAlphabetically(): Flow<List<Book>>

    @Query("SELECT * FROM books_table ORDER BY strftime('%Y-%m-%d', publishDate) DESC")
    fun getBooksSortedByDatePublished(): Flow<List<Book>>

    @Query("SELECT * FROM books_table WHERE id = :bookId")
    fun getBookByItemId(
        bookId: String,
    ): Flow<Book>

    @Query("UPDATE books_table SET bookmarked = :bookmarkedStatus WHERE id = :bookId")
    suspend fun updateBookmarkStatus(
        bookmarkedStatus: Boolean,
        bookId: String,
    )

    @Insert(
        onConflict = OnConflictStrategy.IGNORE,
    )
    suspend fun updateBookList(
        bookList: List<Book>,
    )
}
