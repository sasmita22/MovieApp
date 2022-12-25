package com.hiroshisasmita.feature.presentation.detail.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hiroshisasmita.core.const.LoadImageConst
import com.hiroshisasmita.core.extension.DateFormat
import com.hiroshisasmita.core.extension.LocaleEnum
import com.hiroshisasmita.core.extension.extConvertDateStringFormat
import com.hiroshisasmita.core.extension.extLoadImage
import com.hiroshisasmita.resources.R
import com.hiroshisasmita.feature.databinding.ItemReviewBinding
import com.hiroshisasmita.feature.presentation.model.ReviewUiModel

class ReviewsAdapter(
    private val onSeeFullClickListener: (ReviewUiModel) -> Unit
): PagingDataAdapter<ReviewUiModel, ReviewsAdapter.ViewHolder>(ItemDiffCallback()) {

    inner class ViewHolder(private val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(reviewItem: ReviewUiModel) = with(binding) {
            tvName.text = reviewItem.authorDetails.name.takeIf { it.isNotEmpty() } ?: reviewItem.author
            tvUsername.text = "@${reviewItem.authorDetails.username}"
            ivProfile.extLoadImage(
                LoadImageConst.buildImageUrl(reviewItem.authorDetails.avatarPath),
                R.drawable.dummy_profile
            )
            tvReview.text = reviewItem.content
            tvPublishedAt.text = root.context.getString(R.string.label_review_time, getFormattedDate(reviewItem.updatedAt))
            tvSeeFull.setOnClickListener { onSeeFullClickListener.invoke(reviewItem) }
        }

        private fun getFormattedDate(dateString: String): String {
            return dateString.extConvertDateStringFormat(
                formatBefore = DateFormat.UTC_FORMAT,
                formatAfter = DateFormat.DD_MMM_YYYY_HH_MM_A,
                localeAfter = LocaleEnum.ENGLISH.locale
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindData(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

private class ItemDiffCallback: DiffUtil.ItemCallback<ReviewUiModel>() {
    override fun areItemsTheSame(oldItem: ReviewUiModel, newItem: ReviewUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReviewUiModel, newItem: ReviewUiModel): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

}