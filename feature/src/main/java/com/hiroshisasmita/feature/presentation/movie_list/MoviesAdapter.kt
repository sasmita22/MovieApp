package com.hiroshisasmita.feature.presentation.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hiroshisasmita.core.const.LoadImageConst
import com.hiroshisasmita.core.extension.extLoadImage
import com.hiroshisasmita.feature.databinding.ItemMoviesBinding
import com.hiroshisasmita.feature.presentation.model.MovieUiModel

class MoviesAdapter(
    private val onMovieClickListener: (MovieUiModel) -> Unit
): PagingDataAdapter<MovieUiModel, MoviesAdapter.ViewHolder>(ItemDiffCallback()) {

    inner class ViewHolder(private val binding: ItemMoviesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(movieItem: MovieUiModel) = with(binding) {
            tvTitle.text = movieItem.title
            ivImage.extLoadImage(
                LoadImageConst.buildImageUrl(movieItem.posterPath)
            )

            root.setOnClickListener {
                onMovieClickListener.invoke(movieItem)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindData(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

private class ItemDiffCallback: DiffUtil.ItemCallback<MovieUiModel>() {
    override fun areItemsTheSame(oldItem: MovieUiModel, newItem: MovieUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieUiModel, newItem: MovieUiModel): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

}