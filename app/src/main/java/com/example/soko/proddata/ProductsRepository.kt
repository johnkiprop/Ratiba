package com.example.soko.proddata

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData

import com.example.soko.data.RepoService
import com.example.soko.database.AppDatabase
import com.example.soko.model.ProductItems
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ProductsRepository @Inject constructor (private val repoService: RepoService, private val database :AppDatabase
){
    fun getRepoResult(query: String): Flow<PagingData<ProductItems>>{
        // appending '%' so we can allow other characters to be before and after the query string
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { database.reposDao().reposByName(dbQuery) }
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            remoteMediator = ProductsRemoteMediator(
                query,
                repoService,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
    companion object {
        private const val NETWORK_PAGE_SIZE = 15
    }
}