package com.example.movieapp.ui.main

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.movieapp.R
import com.example.movieapp.databinding.MainFragmentBinding
import com.example.movieapp.util.MoviesViewModelFactory
import com.example.movieapp.util.inject
import com.example.movieapp.util.toVisibility
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class MainFragment : Fragment() {

    private val adapter = MovieAdapter()
    private var _binding: MainFragmentBinding? = null
    private var searchJob: Job? = null
    private val binding get() = _binding!!


    @Inject
    lateinit var viewModelFactory: MoviesViewModelFactory


    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)
            .get(MainViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        initAdapter()
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.start()
            viewModel.getMovies(null).collectLatest {
                adapter.submitData(it)
            }
        }


    }

    private fun initAdapter() {
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)
        binding.retryButton.setOnClickListener { adapter.retry() }
        binding.list.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { adapter.retry() },
            footer = MovieLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh !is LoadState.NotLoading) {
                binding.list.visibility = View.GONE
                binding.retryButton.visibility = toVisibility(loadState.refresh is LoadState.Error)
                binding.progressBar.visibility =
                    toVisibility(loadState.refresh is LoadState.Loading)
            } else {
                // We're not actively refreshing
                // So we should show the list
                binding.list.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.retryButton.visibility = View.GONE
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> {
                        loadState.append as LoadState.Error
                    }
                    loadState.prepend is LoadState.Error -> {
                        loadState.prepend as LoadState.Error
                    }
                    else -> {
                        null
                    }
                }
                errorState?.let {
                    Toast.makeText(context, "\uD83D\uDE28 Wooops ${it.error}", Toast.LENGTH_LONG)
                        .show()
                }

            }
        }
    }

    private fun setupToolbar() {
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.topAppBar.menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.progressBar.visibility = View.VISIBLE
                searchJob?.cancel()

                if (searchJob?.isCancelled == true)
                    searchJob = lifecycleScope.launch {
                        viewModel.getMovies(query).collectLatest {
                            adapter.submitData(it)

                        }
                    }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}