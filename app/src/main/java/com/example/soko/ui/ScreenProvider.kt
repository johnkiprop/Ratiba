package com.example.soko.ui

import androidx.fragment.app.Fragment

interface ScreenProvider {
    fun initialScreen(): Fragment?
}