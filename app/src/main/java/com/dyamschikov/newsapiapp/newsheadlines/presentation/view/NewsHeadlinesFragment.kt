package com.dyamschikov.newsapiapp.newsheadlines.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.BaseApplication
import com.dyamschikov.newsapiapp.R
import com.dyamschikov.newsapiapp.databinding.FragmentHeadlinesNewsBinding
import com.dyamschikov.newsapiapp.newsheadlines.presentation.adapter.NewsHeadlinesAdapter
import com.dyamschikov.newsapiapp.newsheadlines.presentation.viewmodel.NewsHeadlinesViewModel
import com.dyamschikov.newsapiapp.utils.EventObserver
import com.dyamschikov.newsapiapp.utils.showSnackbar
import com.dyamschikov.newsapiapp.utils.ui.CustomListDecorator
import javax.inject.Inject

class NewsHeadlinesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<NewsHeadlinesViewModel> { viewModelFactory }
    private lateinit var viewDataBinding: FragmentHeadlinesNewsBinding
    private val adapter = NewsHeadlinesAdapter()
    private var currentCountry: Int = START_POSITION_COUNTRY

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as BaseApplication)
            .appComponent
            .newsHeadlinesComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewDataBinding = FragmentHeadlinesNewsBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel
        viewDataBinding.newsListRv.addItemDecoration(
            CustomListDecorator(
                requireContext(),
                true
            )
        )
        viewDataBinding.newsListRv.adapter = adapter
        viewModel.newsSuccess.observe(viewLifecycleOwner, { articles ->
            adapter.addHeaderAndSubmitList(articles)
            adapter.apply {
                onItemClick = { item ->
                    val action =
                        NewsHeadlinesFragmentDirections.newsHeadlinesFragmentToNewsDetailsFragment(
                            item,
                            articles.toTypedArray()
                        )
                    findNavController().navigate(action)
                }
                onItemHeaderClick = { headerItem ->
                    currentCountry = headerItem
                    fetchData(currentCountry)
                    viewModel.setCurrentCountry(currentCountry)
                }
            }
            viewDataBinding.containerRefresh.isRefreshing = false
        })

        viewModel.requestErrorEvent.observe(viewLifecycleOwner, EventObserver {
            requireView().showSnackbar(resources.getString(R.string.error_massage))
        })

        viewModel.requestOfflineEvent.observe(viewLifecycleOwner, EventObserver {
            requireView().showSnackbar(it)
        })
        swipeRefreshData()
    }

    private fun fetchData(position: Int) {
        val positionId = when (position) {
            0 -> CountryCode.Ukraine.code
            1 -> CountryCode.USA.code
            2 -> CountryCode.GreatBritain.code
            3 -> CountryCode.Poland.code
            4 -> CountryCode.SouthAfrica.code
            else -> CountryCode.Ukraine.code
        }
        viewModel.fetchNews(positionId)
    }

    enum class CountryCode(val code: String) {
        Ukraine("ua"),
        USA("us"),
        GreatBritain("gb"),
        Poland("pl"),
        SouthAfrica("za")
    }

    private fun swipeRefreshData() {
        viewDataBinding.containerRefresh.setOnRefreshListener {
            val currentCountry = if (viewModel.currentCountry.value != null) {
                viewModel.currentCountry.value
            } else {
                START_POSITION_COUNTRY
            }
            currentCountry?.let { fetchData(it) }
        }
    }

    companion object {
        const val START_POSITION_COUNTRY = 0
    }
}