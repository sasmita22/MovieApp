package com.hiroshisasmita.feature.presentation.detail.review

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.hiroshisasmita.core.extension.extGone
import com.hiroshisasmita.core.extension.extVisible
import com.hiroshisasmita.core.functional.DividerItemDecorator
import com.hiroshisasmita.core.functional.PagingLoadStateAdapter
import com.hiroshisasmita.core.platform.BaseViewModelFragment
import com.hiroshisasmita.resources.R
import com.hiroshisasmita.feature.databinding.FragmentMovieReviewBinding
import com.hiroshisasmita.feature.presentation.detail.MovieDetailViewModel
import com.hiroshisasmita.feature.presentation.model.MovieUiModel
import com.hiroshisasmita.feature.presentation.model.ReviewUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieReviewFragment : BaseViewModelFragment<MovieDetailViewModel, FragmentMovieReviewBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() = MovieReviewFragment()
    }

    override val viewModel by viewModels<MovieDetailViewModel>(
        ownerProducer = { requireActivity() }
    )

    private val adapter by lazy {
        ReviewsAdapter(onSeeFullClickListener)
    }

    private val onSeeFullClickListener = { review: ReviewUiModel ->
        openReviewOnBrowser(review.url)
    }

    private fun openReviewOnBrowser(url: String) {
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }.let { startActivity(it) }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieReviewBinding
        get() = FragmentMovieReviewBinding::inflate

    override fun setupViews() = with(binding) {
        setupRecyclerViewDivider()
        setupPagingStateHandler(adapter)
        rvReview.adapter = adapter.withLoadStateFooter(
            PagingLoadStateAdapter() {
                adapter.retry()
            }
        )
        swipeRefresh.setOnRefreshListener {
            adapter.refresh()
            swipeRefresh.isRefreshing = false
        }
    }

    private fun setupPagingStateHandler(adapter: ReviewsAdapter) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                adapter.loadStateFlow.collectLatest {
                    handlePagingState(it)
                }
            }
        }
    }

    private fun handlePagingState(loadState: CombinedLoadStates) = with(binding) {
        pbLoading.isVisible = loadState.refresh is LoadState.Loading
        btRetry.isVisible = loadState.refresh is LoadState.NotLoading && loadState.refresh is LoadState.Error
        rvReview.isVisible = loadState.refresh !is LoadState.Error
        tvNoData.isVisible = loadState.refresh is LoadState.NotLoading && adapter.snapshot().isEmpty()

        checkErrorStateRefresh(loadState)
        checkErrorStateAppend(loadState)
    }

    private fun checkErrorStateRefresh(loadState: CombinedLoadStates) {
        if (loadState.refresh !is LoadState.Error) return

        (loadState.source.refresh as? LoadState.Error)?.error?.let { error ->
            handleErrorApiState(error) {
                binding.tvNoData.extVisible()
                binding.btRetry.extGone()
            }
        }
    }

    private fun checkErrorStateAppend(loadState: CombinedLoadStates) {
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error
            ?: loadState.source.refresh as? LoadState.Error

        errorState?.error?.let { error ->
            handleErrorApiState(error) { /*Nothing to do here*/ }
        }
    }

    private fun setupRecyclerViewDivider() {
        AppCompatResources.getDrawable(requireContext(), R.drawable.divider_decorator)
            ?.let {
                binding.rvReview.addItemDecoration(DividerItemDecorator(it))
            }
    }

    override fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.movieItem
                        .collectLatest { movieItem ->
                            movieItem?.let { fetchReviews(it) }
                        }
                }
            }
        }
    }

    private fun fetchReviews(movieUiModel: MovieUiModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.fetchReviews(movieUiModel.id)
                        .collectLatest {
                            adapter.submitData(it)
                        }
                }
            }
        }
    }
}