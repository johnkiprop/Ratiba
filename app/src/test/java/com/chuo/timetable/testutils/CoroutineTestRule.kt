package com.chuo.timetable.testutils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class CoroutineTestRule( val dispatcher: TestCoroutineDispatcher ) : TestWatcher() {
    override fun starting(description: Description?) {
    super.starting(description)
    Dispatchers.setMain(dispatcher)
        }
    }