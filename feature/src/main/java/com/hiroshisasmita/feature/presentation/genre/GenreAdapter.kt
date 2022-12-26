package com.hiroshisasmita.feature.presentation.genre

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hiroshisasmita.feature.databinding.ItemGenreBinding
import com.hiroshisasmita.feature.presentation.model.GenreUiModel

class GenreAdapter(
    private val onDataChange: (isEmpty: Boolean) -> Unit,
    private val onClickListener: (GenreUiModel) -> Unit
): RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    private var data: List<GenreUiModel> = listOf()

    inner class ViewHolder(private val binding: ItemGenreBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: GenreUiModel) = with (binding) {
            tvGenre.text = item.name
            root.setOnClickListener {
                onClickListener.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount() = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<GenreUiModel>) {
        this.data = data
        onDataChange.invoke(data.isEmpty())
        notifyDataSetChanged()
    }
}