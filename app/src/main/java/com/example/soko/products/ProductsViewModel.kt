package com.example.soko.products

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.soko.di.ScreenScope

import com.example.soko.model.ProductItems
import com.example.soko.proddata.ProductsRepository
import com.example.soko.viewmodel.AssistedSavedStateViewModelFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsViewModel
@AssistedInject
constructor( private val productsRepository: ProductsRepository,
            @Assisted private val savedStateHandle: SavedStateHandle
            )
    : ViewModel() {
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<ProductItems>>? = null
    @AssistedInject.Factory
    interface Factory : AssistedSavedStateViewModelFactory<@JvmSuppressWildcards ProductsViewModel>{
        override fun create(savedStateHandle: SavedStateHandle): ProductsViewModel
    }

    fun getResult(query:String) : Flow<PagingData<ProductItems>> {
        val lastResult = currentSearchResult
        val queryString = savedStateHandle["query"] ?: query
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<ProductItems>> = productsRepository.getRepoResult(queryString).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
}
}

