package com.example.soko.products



import com.example.soko.di.ScreenScope
import dagger.Subcomponent
import dagger.android.AndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ScreenScope
@Subcomponent()
 interface ProductsComponent: AndroidInjector<ProductsFragment> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<ProductsFragment>() {

    }
}