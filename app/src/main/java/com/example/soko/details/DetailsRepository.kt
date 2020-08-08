package com.example.soko.details

import com.example.soko.data.RepoService
import com.example.soko.database.AppDatabase
import com.example.soko.model.ProductItems
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailsRepository @Inject
constructor(private val appDatabase: AppDatabase, private val dispatcher:CoroutineDispatcher){

    @ExperimentalCoroutinesApi
    fun productDetails(id: Int): Flow<Result<ProductItems>> {
    return flow {
        val product = appDatabase.reposDao().repoById(id)
        emit(Result.success(product))
    }.flowOn(dispatcher)
}
}