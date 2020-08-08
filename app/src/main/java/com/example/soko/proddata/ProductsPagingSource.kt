package com.example.soko.proddata


import androidx.paging.PagingSource
import com.example.soko.data.RepoService
import com.example.soko.di.ScreenScope
import com.example.soko.model.ProductItems
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class ProductsPagingSource @Inject constructor(private val repoService: RepoService,
                                               private val query: String)
    : PagingSource<Int, ProductItems>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductItems> {
          try {
              // Start refresh at page 1 if undefined.
              val nextPageNumber = params.key ?: 1
              val response = repoService.getProductItems(query,nextPageNumber, params.loadSize)
              return LoadResult.Page(
                data = response.items,
                prevKey = null, // Only paging forward.
                  nextKey = if (response.items.isEmpty()) null else nextPageNumber + 1
            )

        }catch (e:Exception){
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
              Timber.e(e)
            return LoadResult.Error(e)

        }
        catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            Timber.e(e)
            return LoadResult.Error(e)
        }

    }


}