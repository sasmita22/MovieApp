package com.hiroshisasmita.feature.presentation.detail.review

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hiroshisasmita.core.functional.DividerItemDecorator
import com.hiroshisasmita.core.platform.BaseViewModelFragment
import com.hiroshisasmita.resources.R
import com.hiroshisasmita.feature.databinding.FragmentMovieReviewBinding
import com.hiroshisasmita.feature.presentation.detail.MovieDetailViewModel
import com.hiroshisasmita.feature.presentation.model.MovieUiModel
import com.hiroshisasmita.feature.presentation.model.ReviewUiModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


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
        rvReview.adapter = adapter
    }

    private fun setupRecyclerViewDivider() {
        AppCompatResources.getDrawable(requireContext(), R.drawable.divider_decorator)
            ?.let {
                binding.rvReview.addItemDecoration(DividerItemDecorator(it))
            }
    }

    override fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
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
            repeatOnLifecycle(Lifecycle.State.STARTED) {
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