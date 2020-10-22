package com.chuo.timetable.products


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.map

import com.chuo.timetable.data.ProductItemsResponse
import com.chuo.timetable.data.RepoService
import com.chuo.timetable.database.AppDatabase
import com.chuo.timetable.model.ProductItems

import com.chuo.timetable.proddata.ProductsRepository
import com.chuo.timetable.testutils.MockPagingSource
import com.chuo.timetable.testutils.TestCoroutineRule
import com.chuo.timetable.testutils.TestUtils

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock

import kotlinx.coroutines.ExperimentalCoroutinesApi

import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest

import org.amshove.kluent.`should be equal to`

import org.junit.Test

import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

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