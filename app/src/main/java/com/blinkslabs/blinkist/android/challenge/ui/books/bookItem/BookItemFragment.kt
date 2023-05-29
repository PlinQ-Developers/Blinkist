package com.blinkslabs.blinkist.android.challenge.ui.books.bookItem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.blinkslabs.blinkist.android.challenge.R
import com.blinkslabs.blinkist.android.challenge.databinding.FragmentBookItemBinding
import com.blinkslabs.blinkist.android.challenge.domain.models.Book
import com.google.android.material.snackbar.Snackbar
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
        binding.bookItemFragmentBackButton.setOnClickListener {
            viewModel.setEvent(
                event = BookItemFragmentContract.Event.OnUserClickBackIcon,
            )
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                initView(state.bookItem)
            }
        }
        lifecycleScope.launch {
            viewModel.effect.collect { effect ->
                collectEffects(effect)
            }
        }
    }

    private fun initView(bookItem: Book?) {
        bookItem?.let { book: Book ->
            val bookmarked = book.bookmarked
            binding.apply {
                Picasso.get().load(book.coverImageUrl).into(bookItemFragmentBookCoverImage)
                bookItemFragmentBookTitle.text = book.name
                bookItemFragmentBookPublishYear.text = book.publishDate.split("-")[0]
                bookItemFragmentBookAuthor.text = book.author
                bookItemFragmentSynopsis.text = book.description
                bookItemFragmentBookCategory.text = book.category
                bookItemFragmentBookPages.text = "${book.pages} Pages"
            }
            if (bookmarked) {
                binding.bookItemFragmentBookmarkButton.setImageDrawable(resources.getDrawable(R.drawable.ic_bookmark_active))
            }

            binding.bookItemFragmentBookmarkButton.setOnClickListener {
                viewModel.setEvent(
                    event = BookItemFragmentContract.Event.OnUserClickBookmarkIcon(
                        bookId = book.id,
                        bookmarkStatus = !bookmarked,
                    ),
                )
            }
        }
    }

    private fun collectEffects(effect: BookItemFragmentContract.Effect) {
        when (effect) {
            is BookItemFragmentContract.Effect.NavigateToBookListFragment -> findNavController().popBackStack()
            is BookItemFragmentContract.Effect.ShowErrorMessage -> showErrorMessage(effect.message)
        }
    }

    private fun showErrorMessage(message: String) {
        val errorSnack = Snackbar.make(
            requireView(),
            "Error: $message",
            Snackbar.LENGTH_INDEFINITE,
        )

        errorSnack.setAction("Ok") {
            errorSnack.dismiss()
        }
        errorSnack.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
