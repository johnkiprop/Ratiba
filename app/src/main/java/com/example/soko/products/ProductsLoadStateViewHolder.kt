package com.example.soko.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.soko.R
import com.example.soko.databinding.RepoLoadStateFooterViewItemBinding


class ProductsLoadStateViewHolder(
    private val binding: RepoLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.visibility = toVisibility(loadState is LoadState.Loading)
        binding.retryButton.visibility = toVisibility(loadState !is LoadState.Loading)
        binding.errorMsg.visibility = toVisibility(loadState !is LoadState.Loading)
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): ProductsLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_load_state_footer_view_item, parent, false)
            val binding = RepoLoadStateFooterViewItemBinding.bind(view)
            return ProductsLoadStateViewHolder(binding, retry)
        }
    }

}