package com.chuo.timetable.repository

import com.chuo.timetable.testutils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

@ExperimentalCoroutinesApi
class FirebaseRepositoryTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun `obschedule`()  = testCoroutineRule.runBlockingTest{
        val dispatcher = TestCoroutineDispatcher()
        val repository = FirebaseRepository(dispatcher)
        val flow = repository.observeSchedule("Form One", "Monday")
        val result = flow.single()
        assert(result.isSuccess) { "Test Failed" }
    }
}