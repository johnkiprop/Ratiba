package com.example.soko.database.repo

import androidx.paging.PagingSource
import androidx.room.*
import com.example.soko.model.ProductItems

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<ProductItems>)

    @Query("SELECT * FROM products WHERE name LIKE :queryString ORDER BY name ASC")
    fun reposByName(queryString: String): PagingSource<Int, ProductItems>

    @Query("SELECT * FROM products WHERE id LIKE :id ORDER BY id ASC")
    suspend fun repoById(id:Int): ProductItems

    @Query("DELETE FROM products")
    suspend fun clearRepos()
}