package com.example.soko.testutils

import androidx.paging.PagingSource
import com.example.soko.data.ProductItemsResponse
import com.example.soko.model.ProductItems

class MockPagingSource(private val items: List<ProductItems>) : PagingSource<Int, ProductItems>() {
    private val testUtils = TestUtils()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductItems> {
        val key = params.key ?: 0
        return LoadResult.Page(
            data = items,
            prevKey = null, // Only paging forward.
            nextKey = if (items.isEmpty()) null else key + 1
        )
    }
}