package com.dyamschikov.newsapiapp.newsdetailsnews.presentation.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.BaseApplication
import com.dyamschikov.newsapiapp.databinding.FragmentNewsDetailsBinding
import com.dyamschikov.newsapiapp.newsdetailsnews.presentation.adapter.NewsDetailsAdapter
import com.dyamschikov.newsapiapp.newsdetailsnews.presentation.viewmodel.NewsDetailsViewModel
import javax.inject.Inject

class NewsDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<NewsDetailsViewModel> { viewModelFactory }
    private lateinit var viewDataBinding: FragmentNewsDetailsBinding
    private val args: NewsDetailsFragmentArgs by navArgs()
    private val adapter = NewsDetailsAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as BaseApplication).appComponent.newsDetailsComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewDataBinding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.vm = viewModel

        val argArticleList = args.articleList.toList()
        adapter.submitList(argArticleList)
        adapter.apply {
            onItemUrlClick = { item ->
                openExternalNews(item)
            }
            onItemShareClick = { item ->
                shareNews(item)
            }
        }

        val positionValue = when (val positionVp = viewModel.articlePosition.value) {
            null -> args.articlePosition
            else -> positionVp
        }

        viewDataBinding.newsDetailsVp.doOnLayout {
            viewDataBinding.newsDetailsVp.setCurrentItem(positionValue, false)
        }

        viewDataBinding.newsDetailsVp.adapter = adapter

        val newsDetailsPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setCurrentItemVp(position)
            }
        }
        viewDataBinding.newsDetailsVp.registerOnPageChangeCallback(newsDetailsPageChangeCallback)
    }

    private fun openExternalNews(urlNews: String?) {
        val externalIntent = Intent(Intent.ACTION_VIEW)
        externalIntent.data = Uri.parse(urlNews)
        startActivity(externalIntent)
    }

    private fun shareNews(urlNews: String?) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, urlNews)
            type = INTENT_EXTRA_TYPE
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    companion object {
        const val INTENT_EXTRA_TYPE = "text/plain"
    }
}