package com.chuo.timetable.scheduler

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.chuo.timetable.model.Schedule
import com.chuo.timetable.testutils.*
import com.chuo.timetable.testutils.LiveDataTestUtil.getOrAwaitValue
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


@ExperimentalCoroutinesApi
@ExtendWith(LiveDataTestExtension::class)
class SchedulerViewModelTest {
    val repository = mock<FakeRepository>()
    val viewState = mock<SchedulerViewState>()
    val savedState = mock<SavedStateHandle>()
    val viewmodel by lazy {SchedulerViewModel(repository,viewState, savedState )}


    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun checkSignIn() {
    }


    @Test
    fun `retrieve schedule`()=testCoroutineRule.runBlockingTest {
        val schedule =   Schedule(
            "Form One", "8:00",
            "10:00", "Form One Timetable", "kiprop@gmail.com"
        )

        val result = Result.success(schedule)
        val channel = Channel<Result<Schedule>>()
        val flow = channel.consumeAsFlow()
        val mutableLiveData = MutableLiveData<List<Schedule>>()
        mutableLiveData.postValue(listOf(schedule))

        whenever(repository.observeSchedule("Form One", "Monday")) doReturn flow as Flow<Result<List<Schedule>>>
        whenever(repository.repoLiveData()) doReturn mutableLiveData

        //producer sending values
        launch{ channel.send(result)}
        viewmodel.handleListenForChanges()


        assertEquals(viewState.listener, false)
       assertEquals(viewState.itemsLiveData!!.getOrAwaitValue(), testSchedule )
    }
}