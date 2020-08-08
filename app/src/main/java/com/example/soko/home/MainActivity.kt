package com.example.soko.home

import android.app.SearchManager
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.soko.R
import com.example.soko.base.BaseActivity
import com.example.soko.products.ProductsFragment


class MainActivity : BaseActivity(){
   override fun initialScreen(): Fragment? {
        return ProductsFragment().newInstance()
    }

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }


}
