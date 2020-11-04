package com.chuo.timetable.testutils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import strikt.api.expectThat
import kotlinx.coroutines.flow.collect
import strikt.assertions.contains

class TestCollector<T> {
    private val values = mutableListOf<T>()

    fun test(scope: CoroutineScope, flow: Flow<T>): Job {
        return scope.launch { flow.collect { values.add(it) } }
    }

    fun assertValues(vararg _values: T) {
        expectThat(values).contains(_values.toList())
    }
}