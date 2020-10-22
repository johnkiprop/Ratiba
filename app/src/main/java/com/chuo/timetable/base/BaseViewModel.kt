package com.chuo.timetable.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chuo.timetable.login.LoginFragment
import com.chuo.timetable.repository.FirebaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel<S: ViewState>(
    val firebaseRepository: FirebaseRepository,
    var viewState: S): ViewModel() {
    protected val stateLiveData = MutableLiveData<ViewState>()
    private val networkJob = Job()
    protected val scope = CoroutineScope(Dispatchers.IO + networkJob)

    fun loginState(): Boolean{
        return viewState.newFragment
    }

    fun checkEmailVerification() : Boolean{
        return isEmailVerified()
    }
    private  fun isEmailVerified() : Boolean{
        return if (firebaseRepository.user() == null) {
            false
        }else{
            firebaseRepository.user()!!.isEmailVerified
        }

    }

    fun handleSignOut() {
        firebaseRepository.auth.signOut()
        viewState.newFragment = true
        updateUi()
    }


    fun getState(): MutableLiveData<ViewState> {
        return this.stateLiveData
    }

    fun resetNewFragment() {
        viewState.newFragment = false
        updateUi()
    }

    fun updateUi() {
        stateLiveData.postValue(viewState)
    }

}