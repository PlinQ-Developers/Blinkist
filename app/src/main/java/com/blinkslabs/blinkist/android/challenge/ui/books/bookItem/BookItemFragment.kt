package com.blinkslabs.blinkist.android.challenge.ui.books.bookItem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.blinkslabs.blinkist.android.challenge.databinding.FragmentBookItemBinding
import com.blinkslabs.blinkist.android.challenge.domain.models.Book
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookItemFragment : Fragment() {

    private lateinit var binding: FragmentBookItemBinding
    private val viewModel: BookItemFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBookItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
        initObservers()
    }

    private fun initButtons() {
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                initView(state.bookItem)
            }
        }
        lifecycleScope.launch {
            viewModel.effect.collect { effect ->
            }
        }
    }

    private fun initView(bookItem: Book?) {
        bookItem?.let { book: Book ->
            binding.apply {
                Picasso.get().load(book.coverImageUrl).into(bookItemFragmentBookCoverImage)
                bookItemFragmentBookTitle.text = book.name
                bookItemFragmentBookPublishYear.text = book.publishDate.split("-")[0]
                bookItemFragmentBookDescription.text = book.author
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
