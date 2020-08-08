package com.example.soko.products


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.map

import com.example.soko.data.ProductItemsResponse
import com.example.soko.data.RepoService
import com.example.soko.database.AppDatabase
import com.example.soko.database.repo.RemoteKeys
import com.example.soko.model.ProductItems

import com.example.soko.proddata.ProductsRepository
import com.example.soko.testutils.MockPagingSource
import com.example.soko.testutils.TestCoroutineRule
import com.example.soko.testutils.TestUtils

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock

import kotlinx.coroutines.ExperimentalCoroutinesApi

import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockWebServer

import org.amshove.kluent.`should be equal to`
import org.junit.Before

import org.junit.Test

import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ProductsViewModelTest {
    companion object {
        private const val WATCHES: String = "Watches"
    }

    private val testUtils = TestUtils()
    private val testScope = TestCoroutineScope()
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    private val getProductItems =
        testUtils.loadJson("mock/get_products.json",ProductItemsResponse::class.java)
    private val pagingSource: PagingSource<Int, ProductItems>
        get() {
            return MockPagingSource(getProductItems!!.items)
        }

 @Test
 @Throws(Exception::class)
  fun `should get repository flow`() {
     testScope.runBlockingTest {
        val repoService = mock<RepoService>{
             onBlocking{getProductItems(WATCHES, 1, 15)} doReturn getProductItems!!
         }
        val appDatabase = mock<AppDatabase>()
        val repo = ProductsRepository(repoService, appDatabase)
         val flow = repo.getRepoResult(WATCHES).cachedIn(testScope)
          flow.single().map {
             getProductItems!! `should be equal to`  listOf(it)
         }

     }
 }

}