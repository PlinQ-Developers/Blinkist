package com.blinkslabs.blinkist.android.challenge.ui.books.bookList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blinkslabs.blinkist.android.challenge.databinding.BookListItemBinding
import com.blinkslabs.blinkist.android.challenge.domain.models.Book
import com.squareup.picasso.Picasso
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale

class BookListDateAdapter constructor(
    private val listener: BookItemListener,
) : ListAdapter<Book, BookListDateAdapter.BookListAdapterViewHolder>(BookItemComparator()) {

    // Define a list to store the sections and books
    private var sections: MutableList<Section> = mutableListOf()

    inner class BookListAdapterViewHolder(
        private val binding: BookListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val bookPosition = absoluteAdapterPosition
                if (bookPosition != RecyclerView.NO_POSITION) {
                    val bookItem = getItem(bookPosition)
                    listener.onBookItemClickListener(bookItem.id)
                }
            }
        }

        fun bindBookItem(book: Book) {
            binding.apply {
                bookListItemBookTitle.text = book.name
                bookListItemBookDescription.text = book.author
                Picasso.get()
                    .load(book.coverImageUrl)
                    .into(bookListItemBookCover)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BookListAdapterViewHolder {
        return BookListAdapterViewHolder(
            BookListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(
        holder: BookListAdapterViewHolder,
        position: Int,
    ) {
        val bookItem = getItem(position)
        holder.bindBookItem(bookItem)
    }

    override fun getItemCount(): Int {
        return sections.size
    }

    override fun getItemViewType(position: Int): Int {
        return 0 // Assuming you have only one type of item view in the adapter
    }

    override fun onBindViewHolder(
        holder: BookListAdapterViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        val section = sections[position]
        val bookItem = section.book

        holder.bindBookItem(bookItem)
    }

    fun setBooks(books: List<Book>) {
        val groupedBooks = groupBooksByWeeks(books)
        sections.clear()

        for (groupedBook in groupedBooks) {
            val section = Section(groupedBook.key)

            for (book in groupedBook.value) {
                section.addBook(book)
            }

            sections.add(section)
        }

        notifyDataSetChanged()
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

    inner class Section(private val week: String) {
        private val books: MutableList<Book> = mutableListOf()

        val book: Book
            get() = books.first()

        fun addBook(book: Book) {
            books.add(book)
        }
    }

    class BookItemComparator : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(
            oldItem: Book,
            newItem: Book,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Book,
            newItem: Book,
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface BookItemListener {
        fun onBookItemClickListener(bookId: String)
    }
}
