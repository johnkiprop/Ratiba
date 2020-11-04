package com.chuo.timetable.repository

import com.chuo.timetable.model.Schedule
import com.chuo.timetable.testutils.*
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import org.junit.Test

import org.junit.Rule
import strikt.api.expectThat
import strikt.assertions.contains
import java.io.IOException

@ExperimentalCoroutinesApi
class FirebaseRepositoryTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    val repository = FakeRepository()
    val collector = TestCollector<Result<List<Schedule>>>()

    fun LocalBroadcastManager.asFlow(): Flow<Intent> = callbackFlow {
        val receiver: BroadcastReceiver = { offer(it) }
        registerReceiver(receiver)
        awaitClose { unregisterReceiver(receiver) }
    }

    @InternalCoroutinesApi
    @Test
    fun `test repo callbackFlow`()  = testCoroutineRule.runBlockingTest{
        val broadcastManager = LocalBroadcastManager()
        val values = mutableListOf<Int>()
        val job = launch {
            broadcastManager.asFlow().collect {
                values.add(it) }

        }
        broadcastManager.sendBroadcast(0)
        expectThat(values).contains(0)

        broadcastManager.sendBroadcast(1)
        expectThat(values).contains(0, 1)

        broadcastManager.sendBroadcast(2)
        expectThat(values).contains(0, 1, 2)

        job.cancel()

    }
    @Test
    fun `should work with TestCollector`() = testCoroutineRule.runBlockingTest {
        val broadcastManager = LocalBroadcastManager()
        val collector = TestCollector<Intent>()
        val job = collector.test(this, broadcastManager.asFlow())
        broadcastManager.sendBroadcast(0)
        collector.assertValues(0)

        broadcastManager.sendBroadcast(1)
        collector.assertValues(0, 1)

        broadcastManager.sendBroadcast(2)
        collector.assertValues(0, 1, 2)

        job.cancel()
    }
    private fun fetchObserveSchedule() =
       flow{
        //send fake data
        val schedules = listOf(
            Schedule("Form One","8:00",
                "10:00","Form One Timetable","kiprop@gmail.com"),
            Schedule("Form One","8:00",
                "10:00","Form One Timetable","kiprop@gmail.com"))
        emit(Result.success(schedules))
    }
      private fun isAdminState() = flow{
        emit(Result.success(true))
        }

    @Test
    fun `is auth admin success`() = testCoroutineRule.runBlockingTest {
        val repo = mock<FakeRepository>(){
            onBlocking { isAdmin() } doReturn  isAdminState()
        }
        val collector = TestCollector<Result<Boolean>>()
        val job = collector.test(this,
            repo.isAdmin()
        )
        val result =  repository.isAdmin().first()
        collector.assertValues(result)
        job.cancel()
    }
    @Test
    @Throws(Exception::class)
    fun `should return observe schedule`() =testCoroutineRule.runBlockingTest {
        val repo = mock<FakeRepository>(){
            onBlocking { observeSchedule("Form One", "Monday") } doReturn  fetchObserveSchedule()
        }
        val job = collector.test(this,
            repo.observeSchedule("Form One", "Monday") as Flow<Result<List<Schedule>>>
        )
       val result =  repository.observeSchedule("Form One", "Monday").single()
        collector.assertValues(result as Result<List<Schedule>>)
        job.cancel()

    }

//    @Test
//    @Throws(Exception::class)
//    fun `should get error from observe schedule`() = testCoroutineRule.runBlockingTest {
//        val repo = mock<FakeRepository>(){
//            onBlocking { observeSchedule("Form One", "Monday") } doAnswer  {
//             throw IOException()
//            }
//        }
//        val job = collector.test(this,
//            repo.observeSchedule("Form One", "Monday")  as Flow<Result<List<Schedule>>>
//        )
//
//        val result =  repository.observeSchedule("Form One", "Monday")
//
//        collector.assertValues(result as Result<List<Schedule>>)
//        job.cancel()
//    }

}