package com.hiroshisasmita.feature.presentation.genre

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hiroshisasmita.core.extension.extGone
import com.hiroshisasmita.core.platform.BaseViewModelActivity
import com.hiroshisasmita.resources.R
import com.hiroshisasmita.feature.databinding.ActivityGenreBinding
import com.hiroshisasmita.feature.presentation.model.GenreUiModel
import com.hiroshisasmita.feature_bridge.IFeatureNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GenreActivity : BaseViewModelActivity<GenreViewModel, ActivityGenreBinding>() {

    companion object {
        fun navigate(context: Context) = with(context) {
            startActivity(
                Intent(this, GenreActivity::class.java)
            )
        }
    }

    @Inject
    lateinit var featureNavigation: IFeatureNavigation

    override val bindingInflater: (LayoutInflater) -> ActivityGenreBinding
        get() = ActivityGenreBinding::inflate

    private val adapter by lazy {
        GenreAdapter(
            onDataChange,
            onGenreClickItem
        )
    }

    private val onDataChange = { isEmpty: Boolean ->
        setIsDataEmpty(isEmpty)
    }

    private val onGenreClickItem = { genreItem: GenreUiModel ->
        featureNavigation.navigateToMovieList(this, genreItem.id, genreItem.name)
    }

    override fun setupViews() = with(binding) {
        include.btBack.extGone()
        include.tvToolbarTitle.text = getString(R.string.title_toolbar_genre)

        rvGenre.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            viewModel.fetchGenres()
            swipeRefresh.isRefreshing = false
        }

    }

    override val viewModel by viewModels<GenreViewModel>()

    override fun setupObservers() {
        viewModel.fetchGenres()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.result
                        .collectLatest { result ->
                            result.onSuccess { genreList ->
                                genreList?.let { adapter.updateData(it) }
                            }.onError { error ->
                                handleErrorApiState(error) {
                                    setIsDataEmpty(isEmpty = true)
                                }
                            }
                        }
                }
                launch {
                    viewModel.loading
                        .collectLatest { isLoading ->
                            setLoading(isLoading)
                        }
                }
            }
        }
    }

    private fun setIsDataEmpty(isEmpty: Boolean) = with(binding) {
        rvGenre.isVisible = !isEmpty
        tvNoData.isVisible = isEmpty
    }

    private fun setLoading(isLoading: Boolean) {
        binding.pbLoading.isVisible = isLoading
    }
}