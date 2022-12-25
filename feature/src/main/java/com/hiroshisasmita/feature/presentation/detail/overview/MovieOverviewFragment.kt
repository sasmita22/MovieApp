package com.hiroshisasmita.feature.presentation.detail.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hiroshisasmita.core.extension.DateFormat
import com.hiroshisasmita.core.extension.LocaleEnum
import com.hiroshisasmita.core.extension.extConvertDateStringFormat
import com.hiroshisasmita.core.platform.BaseViewModelFragment
import com.hiroshisasmita.feature.databinding.FragmentMovieOverviewBinding
import com.hiroshisasmita.feature.presentation.detail.MovieDetailViewModel
import com.hiroshisasmita.feature.presentation.model.MovieUiModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieOverviewFragment : BaseViewModelFragment<MovieDetailViewModel, FragmentMovieOverviewBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() = MovieOverviewFragment()
    }

    override val viewModel by viewModels<MovieDetailViewModel>(
        ownerProducer = { requireActivity() }
    )

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieOverviewBinding
        get() = FragmentMovieOverviewBinding::inflate

    override fun setupViews() {

    }

    override fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.movieItem.collectLatest { movieItem ->
                        movieItem?.let {
                            setupMovieData(it)
                        }
                    }
                }
            }
        }
    }

    private fun setupMovieData(movieItem: MovieUiModel): Unit = with(binding) {
        tvTitle.text = movieItem.title
        tvRate.text = movieItem.voteAverage.toString()
        tvOverview.text = movieItem.overview
        tvReleaseDate.text = movieItem.releaseDate.extConvertDateStringFormat(
            formatBefore = DateFormat.YYYY_MM_DD_DASH,
            formatAfter = DateFormat.DD_MMMM_YYYY,
            localeAfter = LocaleEnum.ENGLISH.locale
        )
    }
}