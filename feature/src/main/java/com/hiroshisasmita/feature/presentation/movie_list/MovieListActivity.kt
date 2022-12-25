package com.hiroshisasmita.feature.presentation.movie_list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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

        rvMovies.adapter = adapter
    }

    override fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
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