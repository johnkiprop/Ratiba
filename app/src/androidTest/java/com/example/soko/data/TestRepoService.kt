package com.example.soko.data

import javax.inject.Inject

class TestRepoService @Inject constructor(): RepoService {
    override suspend fun getProductItems(
        query: String,
        page: Int,
        itemsPerPage: Int
    ): ProductItemsResponse {
        TODO("Not yet implemented")
    }

}