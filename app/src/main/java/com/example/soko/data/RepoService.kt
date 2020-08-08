package com.example.soko.data


import com.example.soko.model.ProductItems
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoService {
    @GET("display_products.php") suspend fun getProductItems
                ( @Query("q") query: String,
                  @Query("page") page:Int,
                  @Query("per_page") itemsPerPage:Int
                ): ProductItemsResponse
}