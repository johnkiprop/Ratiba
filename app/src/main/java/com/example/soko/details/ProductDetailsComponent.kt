package com.example.soko.details

import com.example.soko.di.ScreenScope
import dagger.BindsInstance
import dagger.Subcomponent
import dagger.android.AndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Named

@ExperimentalCoroutinesApi
@ScreenScope
@Subcomponent()
interface ProductDetailsComponent:AndroidInjector<ProductDetailsFragment>{
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<ProductDetailsFragment>() {
        @BindsInstance
        abstract fun bindProductId(@Named("id") id: String?)
        override fun seedInstance(instance: ProductDetailsFragment?) {
            bindProductId(instance?.arguments?.getString(ProductDetailsFragment.Companion.PRODUCT_ID))

        }
    }
}