package com.example.soko.products

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class ProductLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<ProductsLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: ProductsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ProductsLoadStateViewHolder {
        return ProductsLoadStateViewHolder.create(parent, retry)
    }
}