package com.blinkslabs.blinkist.android.challenge.ui.books.bookList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blinkslabs.blinkist.android.challenge.R
import com.blinkslabs.blinkist.android.challenge.databinding.FragmentBookListBinding
import com.blinkslabs.blinkist.android.challenge.utils.SortOrder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookListFragment : Fragment(), BookListAdapter.BookItemListener {

    private lateinit var binding: FragmentBookListBinding
    private val viewModel: BookListFragmentViewModel by viewModels()
    private lateinit var bookListAdapter: BookListAdapter

    private lateinit var sortOptionsMenu: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBookListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sortOptionsMenu = view.findViewById(R.id.bookListFragment_sortIcon)
        initViews()
        initButtons()
        initObservers()
    }

    private fun initViews() {
        bookListAdapter = BookListAdapter(this)
        binding.bookListFragmentBooksList.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookListAdapter
        }
    }

    private fun initButtons() {
        binding.bookListFragmentRootView.setOnRefreshListener {
            viewModel.setEvent(
                event = BookListFragmentContract.Event.OnUserRefreshFeed,
            )
        }
        binding.bookListFragmentSortIcon.setOnClickListener {
            viewModel.setEvent(
                event = BookListFragmentContract.Event.OnUserClickSortIcon,
            )
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                bookListAdapter.submitBookList(
                    bookList = state.bookList,
                    order = state.filterOrder,
                )
                binding.bookListFragmentRootView.isRefreshing = state.isLoading
            }
        }

        lifecycleScope.launch {
            viewModel.effect.collect { effect ->
                handleEffects(effect)
            }
        }
    }

    private fun handleEffects(
        effect: BookListFragmentContract.Effect,
    ) {
        when (effect) {
            is BookListFragmentContract.Effect.NavigateToBookItem -> navigateToBookItemFragment(
                effect.bookId,
            )

            is BookListFragmentContract.Effect.ShowErrorMessage -> showErrorMessage(errorMessage = effect.message)
            is BookListFragmentContract.Effect.ShowSortFilterMenu -> setupSortOrderMenu()
        }
    }

    override fun onBookItemClickListener(
        bookId: String,
    ) {
        viewModel.setEvent(
            event = BookListFragmentContract.Event.OnUserClickBookItem(
                bookId = bookId,
            ),
        )
    }

    private fun setupSortOrderMenu() {
        val orderMenu = PopupMenu(requireContext(), sortOptionsMenu)
        val menuInflater = orderMenu.menuInflater
        menuInflater.inflate(R.menu.sort_order_menu, orderMenu.menu)
        orderMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.sortByDate -> sortBookList(SortOrder.BY_DATE)
                R.id.sortByName -> sortBookList(SortOrder.BY_NAME)
            }
            true
        }
        orderMenu.show()
    }

    private fun showErrorMessage(
        errorMessage: String,
    ) {
        binding.apply {
            bookListFragmentErrorMessage.text = errorMessage
            binding.bookListFragmentErrorContainer.visibility = View.VISIBLE
        }
    }

    private fun sortBookList(
        sortOrder: SortOrder,
    ) {
        viewModel.setEvent(
            event = BookListFragmentContract.Event.OnUserSortBookList(
                order = sortOrder,
            ),
        )
    }

    private fun navigateToBookItemFragment(
        bookId: String,
    ) {
        val navDirection = BookListFragmentDirections.actionBookListFragmentToBookItemFragment(
            bookItemId = bookId,
        )
        findNavController().navigate(directions = navDirection)
    }
}
