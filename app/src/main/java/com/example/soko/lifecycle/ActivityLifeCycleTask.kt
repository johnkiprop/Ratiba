package com.example.soko.lifecycle

import androidx.appcompat.app.AppCompatActivity


abstract class ActivityLifecycleTask {
    open fun onCreate(activity: AppCompatActivity?) {}
    open fun onStart(activity: AppCompatActivity?) {}
    open  fun onResume(activity: AppCompatActivity?) {}
    open  fun onPause(activity: AppCompatActivity?) {}
    open fun onStop(activity: AppCompatActivity?) {}
    open  fun onDestroy(activity: AppCompatActivity?) {}
}