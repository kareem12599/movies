package com.example.movieapp.ui.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movieapp.databinding.DetailsFragmentBinding
import com.example.movieapp.ui.main.MainViewModel
import com.example.movieapp.util.MoviesViewModelFactory
import com.example.movieapp.util.inject
import javax.inject.Inject

class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null

    private var adapter: MovieImagesAdapter =
        MovieImagesAdapter()

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
    ): View? {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movieLiveData.observe(viewLifecycleOwner, Observer {
            binding.titleHeadline.text = it.title
            binding.cast.text = it.cast.toString()
            binding.rating.text = it.rating.toString()

        })
        viewModel.moviesPicsLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            if (it!= null ) adapter.submitList(it) else {
                binding.list.visibility = View.GONE
                binding.error.text = "Server error "
            }

        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}