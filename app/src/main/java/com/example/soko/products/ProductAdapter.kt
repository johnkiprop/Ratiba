package com.example.soko.products

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.soko.R
import com.example.soko.model.ProductItems
import kotlinx.android.synthetic.main.product_card.view.*


class ProductAdapter(private val listener:RepoClickedListener,
                     diffCallback: DiffUtil.ItemCallback<ProductItems>)
    : PagingDataAdapter<ProductItems, ProductAdapter.RepoViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepoViewHolder {
        var layoutId = R.layout.staggered_product_card_first
        if (viewType == 1) {
            layoutId = R.layout.staggered_product_card_second
        } else if (viewType == 2) {
            layoutId = R.layout.staggered_product_card_third
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId,parent, false)
        return RepoViewHolder(view, listener)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 3
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = getItem(position)
        repo.let { holder.setData(repo!!)}
    }
 class  RepoViewHolder(val view: View, val listener: RepoClickedListener): RecyclerView.ViewHolder(view){
     var productItems:ProductItems? = null
     init {
         view.setOnClickListener { v: View? ->
             if (productItems != null) {
                 listener.onRepoClicked(productItems)
             }
         }
     }
     fun setData(productItems: ProductItems) {
        this.productItems = productItems
         for (i in productItems.images) {
             view.product_title.text = productItems.name
             view.product_price.text = productItems.price
             Log.d("picture", productItems.images.toString())
             Log.d("picha", i.src)
             Glide.with(view.product_image.context)
                 .load(i.src)
                 .override(400, 200)
                 .timeout(7000)
                 .centerCrop()
                 .listener(object : RequestListener<Drawable> {
                     override fun onLoadFailed(
                         e: GlideException?,
                         p1: Any?,
                         p2: Target<Drawable>?,
                         p3: Boolean
                     ): Boolean {
                         // You can also log the individual causes:
                         Log.e("picha", "Load failed", e);
                         // You can also log the individual causes:
                         for (t in e?.rootCauses!!) {
                             Log.e("picha", "Caused by", t)
                         }
                         e.logRootCauses("picha");

                         return false; // Allow calling onLoadFailed on the Target.
                     }

                     override fun onResourceReady(
                         resource: Drawable?,
                         model: Any?,
                         target: Target<Drawable>?,
                         dataSource: com.bumptech.glide.load.DataSource?,
                         isFirstResource: Boolean
                     ): Boolean {
                         return false;
                     }
                 })
                 .into(view.product_image)



             Log.d("image", productItems.images.component1().src)
         }
     }

 }
    interface RepoClickedListener {
        fun onRepoClicked(productItems: ProductItems?)
    }
    object UserComparator : DiffUtil.ItemCallback<ProductItems>() {
        override fun areItemsTheSame(oldItem: ProductItems, newItem: ProductItems): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductItems, newItem: ProductItems): Boolean {
            return oldItem == newItem
        }
    }

}