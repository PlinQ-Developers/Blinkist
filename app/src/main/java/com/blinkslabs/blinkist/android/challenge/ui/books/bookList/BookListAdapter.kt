package com.blinkslabs.blinkist.android.challenge.ui.books.bookList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blinkslabs.blinkist.android.challenge.databinding.BookListItemBinding
import com.blinkslabs.blinkist.android.challenge.databinding.BookListSectionItemBinding
import com.blinkslabs.blinkist.android.challenge.domain.models.Book
import com.blinkslabs.blinkist.android.challenge.utils.SortOrder
import com.squareup.picasso.Picasso
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale

class BookListAdapter(
    private val listener: BookItemListener,
) : RecyclerView.Adapter<BookListAdapter.BaseViewHolder>() {

    private val items: MutableList<Any> = mutableListOf()

    companion object {
        const val SECTION_TYPE = 0
        const val BOOK_TYPE = 1
    }

    inner class SectionViewHolder(private val binding: BookListSectionItemBinding) :
        BaseViewHolder(binding.root) {

        fun bind(sectionTitle: String) {
            binding.bookListSectionItemTitle.text = sectionTitle
        }
    }

    inner class BookViewHolder(private val binding: BookListItemBinding) :
        BaseViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val bookItem = items[position]
                    if (bookItem is Book) {
                        listener.onBookItemClickListener(bookItem.id)
                    }
                }
            }
        }

        fun bind(book: Book) {
            binding.apply {
                bookListItemBookTitle.text = book.name
                bookListItemBookDescription.text = book.author
                bookListItemBookPublishYear.text = book.publishDate.split("-")[0]
                Picasso.get().load(book.coverImageUrl).into(bookListItemBookCover)
                bookListFragmentStars.text = "${book.rating} Rated"
            }
        }
    }

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            SECTION_TYPE -> {
                val binding = BookListSectionItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                SectionViewHolder(binding)
            }
            BOOK_TYPE -> {
                val binding = BookListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                BookViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is SectionViewHolder -> {
                val section = item as String
                holder.bind(section)
            }
            is BookViewHolder -> {
                val book = item as Book
                holder.bind(book)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return if (item is String) {
            SECTION_TYPE
        } else {
            BOOK_TYPE
        }
    }

    fun submitBookList(bookList: List<Book>, order: SortOrder) {
        items.clear()

        // Sort books by name
        val sortedBookList = bookList.sortedBy { it.name }

        // Group books by date (week published)
        val sortedBookListByDate = groupBooksByWeeks(books = bookList)

        // Group books by the first letter of their name
        val booksGroupedByName = sortedBookList.groupBy { it.name.first().toString() }

        // Add sections and books to the items list
        when (order) {
            SortOrder.BY_NAME -> booksGroupedByName.forEach { (sectionTitle, books) ->
                items.add(sectionTitle)
                items.addAll(books)
            }
            SortOrder.BY_DATE -> sortedBookListByDate.forEach { (sectionTitle, books) ->
                items.add(sectionTitle)
                items.addAll(books)
            }
        }

        notifyDataSetChanged()
    }

    interface BookItemListener {
        fun onBookItemClickListener(bookId: String)
    }

    private fun groupBooksByWeeks(books: List<Book>): Map<String, MutableList<Book>> {
        val groupedBooks: MutableMap<String, MutableList<Book>> = LinkedHashMap()

        for (book in books) {
            val week = getWeekFromDate(book.publishDate)
            val bookList = groupedBooks.getOrPut(week) { mutableListOf() }
            bookList.add(book)
        }

        return groupedBooks
    }

    private fun getWeekFromDate(dateString: String): String {
        val date = LocalDate.parse(dateString)
        val weekStart = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val weekEnd = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.getDefault())

        return "${weekStart.format(formatter)} - ${weekEnd.format(formatter)}"
    }
}
