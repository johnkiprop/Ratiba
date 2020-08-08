package com.example.soko.products

import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.soko.model.ProductItems

class UserAdapter(diffCallback: DiffUtil.ItemCallback<ProductItems>) :
    PagingDataAdapter<ProductItems, UserViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        return UserViewHolder(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        if (item != null) {
            holder.bind(item)
        }


    }
    object UserComparator : DiffUtil.ItemCallback<ProductItems>() {
        override fun areItemsTheSame(oldItem: ProductItems, newItem:ProductItems): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductItems, newItem: ProductItems): Boolean {
            return oldItem == newItem
        }
    }

}
