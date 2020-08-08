package com.example.soko.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.soko.R
import com.example.soko.base.BaseFragment
import com.example.soko.databinding.DetailsScreenBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

class ProductDetailsFragment: BaseFragment<DetailsScreenBinding>(){
    companion object {
        const val PRODUCT_ID: String = "id"
    }
    private val viewModel: DetailsViewModel by viewModels()
    private fun observeViewModel(view:View){
     viewModel.productDetails.observe(viewLifecycleOwner){productItems ->

     }
        viewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (!isError) return@observe
            Snackbar.make(
                view,
                R.string.error_message,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
    fun newInstance(id:String): Fragment? {
        val bundle = Bundle()
        bundle.putString("instance_id", UUID.randomUUID().toString())
        bundle.putString(PRODUCT_ID, id)
        val fragment: Fragment = ProductDetailsFragment()
        fragment.arguments = bundle
        return fragment
    }
    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): DetailsScreenBinding =
        DetailsScreenBinding.inflate(inflater, container, false)

    @ExperimentalCoroutinesApi
    override fun onViewBound(view: View) {
        viewModel.getProductItem(PRODUCT_ID.toInt())
    }


}