package com.hiroshisasmita.feature.presentation.detail

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayoutMediator
import com.hiroshisasmita.core.platform.BaseViewModelActivity
import com.hiroshisasmita.resources.R
import com.hiroshisasmita.feature.databinding.ActivityMovieDetailBinding
import com.hiroshisasmita.feature.presentation.model.MovieUiModel
import com.hiroshisasmita.feature.presentation.model.MovieVideoUiModel
import com.hiroshisasmita.feature_bridge.MovieDataIntent
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailActivity : BaseViewModelActivity<MovieDetailViewModel, ActivityMovieDetailBinding>() {

    companion object {
        private const val MOVIE_ITEM = "MOVIE_ITEM"
        fun navigate(context: Context, movieDataIntent: MovieDataIntent) = with(context) {
            startActivity(
                Intent(this, MovieDetailActivity::class.java).apply {
                    putExtra(MOVIE_ITEM, movieDataIntent)
                }
            )
        }
    }

    override val viewModel by viewModels<MovieDetailViewModel>()

    override val bindingInflater: (LayoutInflater) -> ActivityMovieDetailBinding
        get() = ActivityMovieDetailBinding::inflate

    private val viewPagerAdapter by lazy {
        MovieDetailPagerAdapter(supportFragmentManager, lifecycle)
    }

    private val movieItem by lazy {
        intent.getParcelableExtra<MovieDataIntent>(MOVIE_ITEM)
            ?.let { movieDataIntent ->
                MovieUiModel.parse(movieDataIntent)
            }
    }

    override fun setupViews(): Unit = with (binding) {
        include.btBack.setOnClickListener { onBackPressed() }
        include.tvToolbarTitle.text = getString(R.string.title_toolbar_detail)
        setupViewPager(this)
        lifecycle.addObserver(youtubePlayer)

        movieItem?.let { movieItem ->
            viewModel.setMovieData(movieItem)
            viewModel.fetchVideos(movieItem.id)
        }
    }

    private fun setupViewPager(binding: ActivityMovieDetailBinding) = with (binding) {
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.title_overview)
                else -> tab.text = getString(R.string.title_review)
            }
        }.attach()
    }

    override fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.videoResult
                    .collectLatest { result ->
                        result.onSuccess { movieVideo ->
                            movieVideo?.let { setupVideo(it)  }
                        }.onError { error ->
                            handleErrorApiState(error) { /*Nothing to do here*/ }
                        }
                    }
            }
        }

    }

    private fun setupVideo(movieVideo: MovieVideoUiModel) {
        binding.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(movieVideo.key, 0f)
            }
        })
    }

}