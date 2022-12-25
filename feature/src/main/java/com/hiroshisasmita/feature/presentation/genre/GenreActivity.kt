package com.hiroshisasmita.feature.presentation.genre

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hiroshisasmita.core.extension.extGone
import com.hiroshisasmita.core.platform.BaseViewModelActivity
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
        GenreAdapter(onGenreClickItem)
    }

    private val onGenreClickItem = { genreItem: GenreUiModel ->
        featureNavigation.navigateToMovieList(this, genreItem.id, genreItem.name)
    }

    override fun setupViews() = with(binding) {
        include.btBack.extGone()
        include.tvToolbarTitle.text = "List of Movie Genres"

        rvGenre.adapter = adapter
    }

    override val viewModel by viewModels<GenreViewModel>()

    override fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.result
                        .collectLatest { result ->
                            result.onSuccess {
                                adapter.updateData(it)
                            }.onError {

                            }
                        }
                }
                launch {
                    viewModel.loading
                        .collectLatest { isLoading ->

                        }
                }
            }
        }
    }
}