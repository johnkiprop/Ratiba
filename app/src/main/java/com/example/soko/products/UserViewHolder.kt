package com.example.soko.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.soko.R
import com.example.soko.model.ProductItems

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.product_title)
    private val price: TextView = view.findViewById(R.id.product_price)
    private val image: ImageView = view.findViewById(R.id.product_image)
    private var repo: ProductItems? = null
    fun bind(repo: ProductItems?) {
        if (repo == null) {
       //     val resources = itemView.resources
            price.visibility = View.GONE
            image.visibility = View.GONE

        } else {
            showRepoData(repo)
        }
    }
    private fun showRepoData(repo: ProductItems) {
        this.repo = repo
        name.text = repo.name
        price.text = repo.price
        Glide.with(image.context)
            .load(repo.images)
            .into(image)
    }
    companion object {
        fun create(parent: ViewGroup): UserViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.product_card, parent, false)
            return UserViewHolder(view)
        }
    }
}
