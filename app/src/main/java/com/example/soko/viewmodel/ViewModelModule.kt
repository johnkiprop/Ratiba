package com.example.soko.viewmodel

import androidx.lifecycle.ViewModel
import com.example.soko.products.ProductsViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@AssistedModule
@Module(includes=[AssistedInject_ViewModelModule::class])
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProductsViewModel::class)
    abstract fun bindListViewModel(f: ProductsViewModel.Factory): AssistedSavedStateViewModelFactory< out ViewModel>

//    @Binds
//    @IntoMap
//    @ViewModelKey(ProductsViewModel::class)
//    abstract fun bindListViewModel(f: ProductsViewModel): ViewModel

}