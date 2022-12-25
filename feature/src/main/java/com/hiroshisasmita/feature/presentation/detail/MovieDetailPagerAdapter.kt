package com.hiroshisasmita.feature.presentation.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hiroshisasmita.feature.presentation.detail.overview.MovieOverviewFragment
import com.hiroshisasmita.feature.presentation.detail.review.MovieReviewFragment

class MovieDetailPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {
    companion object {
        const val NUM_TABS = 2
    }

    override fun getItemCount() = NUM_TABS

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                MovieOverviewFragment.newInstance()
            }
            else -> {
                MovieReviewFragment.newInstance()
            }
        }
    }
}