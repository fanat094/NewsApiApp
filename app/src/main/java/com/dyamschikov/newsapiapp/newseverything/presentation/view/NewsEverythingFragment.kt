package com.dyamschikov.newsapiapp.newseverything.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.BaseApplication
import com.dyamschikov.newsapiapp.R
import com.dyamschikov.newsapiapp.databinding.FragmentNewsEverythingBinding
import com.dyamschikov.newsapiapp.newseverything.presentation.adapter.NewsEverythingAdapter
import com.dyamschikov.newsapiapp.newseverything.presentation.adapter.ReposLoadStateAdapter
import com.dyamschikov.newsapiapp.newseverything.presentation.viewmodel.NewsEverythingViewModel
import com.dyamschikov.newsapiapp.utils.showSnackbar
import com.dyamschikov.newsapiapp.utils.ui.CustomListDecorator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsEverythingFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<NewsEverythingViewModel> { viewModelFactory }
    private lateinit var viewDataBinding: FragmentNewsEverythingBinding
    private val adapter = NewsEverythingAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity()
            .application as BaseApplication)
            .appComponent.newsEverythingComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewDataBinding = FragmentNewsEverythingBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel
        initAdapter()
        viewModel.newsEverythingSuccess.observe(viewLifecycleOwner, { articles ->
            lifecycleScope.launch { adapter.submitData(articles) }
            adapter.apply {
                onItemClick = { item ->
                    val action = NewsEverythingFragmentDirections.newsEverythingFragmentToNewsDetailsFragment(
                        item,
                        adapter.snapshot().items.toTypedArray()
                    )
                    findNavController().navigate(action)
                }
            }
        })
    }

    private fun initAdapter() {
        viewDataBinding.newsEverythingListRv.addItemDecoration(
            CustomListDecorator(
                requireContext(),
                false
            )
        )
        viewDataBinding.newsEverythingListRv.adapter = adapter.withLoadStateFooter(
            footer = ReposLoadStateAdapter { adapter.retry() }
        )

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadState ->
                viewDataBinding.newsEverythingPb.isVisible = loadState.refresh is LoadState.Loading
                viewDataBinding.newsEverythingListRv.isVisible = loadState.refresh is LoadState.NotLoading

                val errorState = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                errorState?.let {
                    requireView().showSnackbar(resources.getString(R.string.error_massage))
                    viewModel.isEmptyData.set(true)
                }
            }
        }
    }
}