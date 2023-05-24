package com.blinkslabs.blinkist.android.challenge.ui.books.bookList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.blinkslabs.blinkist.android.challenge.databinding.FragmentBookListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookListFragment : Fragment() {

    private lateinit var binding: FragmentBookListBinding
    private val viewModel: BookListFragmentViewModel by viewModels()

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
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                Toast.makeText(
                    requireContext(),
                    "List: ${state.bookList}",
                    Toast.LENGTH_LONG,
                ).show()
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
            is BookListFragmentContract.Effect.NavigateToBookItem -> {}
            is BookListFragmentContract.Effect.ShowErrorMessage -> {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
