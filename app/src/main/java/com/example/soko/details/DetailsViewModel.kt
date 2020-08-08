package com.example.soko.details

import androidx.lifecycle.*
import com.example.soko.di.ScreenScope
import com.example.soko.model.ProductItems
import com.example.soko.utils.Resource
import com.example.soko.viewmodel.AssistedSavedStateViewModelFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
@ScreenScope
class DetailsViewModel @Inject
constructor( private val detailsRepository: DetailsRepository) : ViewModel(){
   private val scope = CoroutineScope(Dispatchers.Main)
    private val _productDetails = MutableLiveData<ProductItems>()
    val productDetails: LiveData<ProductItems> = _productDetails

    private val _isError = MutableLiveData<Boolean>(false)
    val isError: LiveData<Boolean> = _isError

    @ExperimentalCoroutinesApi
    fun getProductItem(id:Int){
        scope.launch {
          val flow =  detailsRepository.productDetails(id)
              flow .collect {result:Result<ProductItems>->
                when{
                    result.isSuccess->{
                        _productDetails.value = result.getOrNull().also { data ->
                            Timber.i("success getting product details: $data")
                        }
                    }
                    result.isFailure->{
                        Timber.e(result.exceptionOrNull(), "error getting product details")
                        _isError.value = true
                    }

                }
               }

        }
    }


}