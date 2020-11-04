package com.chuo.timetable.testutils

typealias BroadcastReceiver = (Int) -> Unit
typealias Intent = Int

class LocalBroadcastManager {
    private val receivers = mutableSetOf<BroadcastReceiver>()

    fun sendBroadcast(intent: Intent) {
        receivers.forEach { it(intent) }
    }

    fun registerReceiver(receiver: BroadcastReceiver) {
        receivers.add(receiver)
    }

    fun unregisterReceiver(receiver: BroadcastReceiver) {
        receivers.remove(receiver)
    }
}