package com.hiroshisasmita.feature.presentation.movie_list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.hiroshisasmita.core.extension.extGone
import com.hiroshisasmita.core.extension.extVisible
import com.hiroshisasmita.core.functional.PagingLoadStateAdapter
import com.hiroshisasmita.core.platform.BaseViewModelActivity
import com.hiroshisasmita.feature.databinding.ActivityMovieListBinding
import com.hiroshisasmita.feature.presentation.model.MovieUiModel
import com.hiroshisasmita.feature_bridge.IFeatureNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieListActivity : BaseViewModelActivity<MovieListViewModel, ActivityMovieListBinding>() {

    companion object {
        private const val GENRE_ID = "GENRE_ID"
        private const val GENRE_NAME = "GENRE_NAME"

        fun navigate(context: Context, genreId: Int, genreName: String) = with(context) {
            startActivity(
                Intent(
                    this, MovieListActivity::class.java
                ).apply {
                    putExtra(GENRE_ID, genreId)
                    putExtra(GENRE_NAME, genreName)
                }
            )
        }
    }

    @Inject
    lateinit var featureNavigation: IFeatureNavigation

    private val adapter by lazy {
        MoviesAdapter(onMovieClickListener)
    }

    private val onMovieClickListener = { movie: MovieUiModel ->
        featureNavigation.navigateToMovieDetail(this, movie.toDataIntent())
    }

    override val viewModel by viewModels<MovieListViewModel>()

    override val bindingInflater: (LayoutInflater) -> ActivityMovieListBinding
        get() = ActivityMovieListBinding::inflate

    override fun setupViews() = with(binding) {
        include.btBack.setOnClickListener { onBackPressed() }
        include.tvToolbarTitle.text = intent.getStringExtra(GENRE_NAME)

        rvMovies.adapter = adapter.withLoadStateFooter(
            PagingLoadStateAdapter() {
                adapter.retry()
            }
        )
        setupPagingStateHandler(adapter)

        swipeRefresh.setOnRefreshListener {
            adapter.refresh()
            swipeRefresh.isRefreshing = false
        }
    }

    private fun setupPagingStateHandler(adapter: MoviesAdapter) {
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
        rvMovies.isVisible = loadState.refresh !is LoadState.Error
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

    override fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.fetchMovies(intent.getIntExtra(GENRE_ID, -1))
                        .collectLatest {
                            adapter.submitData(it)
                        }
                }
            }
        }

    }
}