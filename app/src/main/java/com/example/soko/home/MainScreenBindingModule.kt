package com.example.soko.home

import androidx.fragment.app.Fragment
import com.example.soko.details.ProductDetailsComponent
import com.example.soko.details.ProductDetailsFragment
import com.example.soko.di.FragmentKey
import com.example.soko.products.ProductsComponent
import com.example.soko.products.ProductsFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module(subcomponents = [ProductsComponent::class , ProductDetailsComponent::class])
public abstract class MainScreenBindingModule{
@ExperimentalCoroutinesApi
@Binds
@IntoMap
@FragmentKey(ProductsFragment::class)
abstract fun bindingProductsInjector(builder: ProductsComponent.Builder?): AndroidInjector.Factory<out Fragment?>?

    @ExperimentalCoroutinesApi
    @Binds
    @IntoMap
    @FragmentKey(ProductDetailsFragment::class)
    abstract fun bindingProductDetailsInjector(builder: ProductDetailsComponent.Builder?): AndroidInjector.Factory<out Fragment?>?
}