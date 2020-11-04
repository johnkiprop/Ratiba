package com.chuo.timetable.scheduler

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.chuo.timetable.base.BaseViewModel
import com.chuo.timetable.model.Schedule
import com.chuo.timetable.model.Teacher
import com.chuo.timetable.repository.FirebaseRepository
import com.chuo.timetable.repository.Repository
import com.chuo.timetable.viewmodel.AssistedSavedStateViewModelFactory
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
class SchedulerViewModel @AssistedInject
constructor(firebaseRepository: Repository,
            viewState: SchedulerViewState,
            @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel<SchedulerViewState>(firebaseRepository, viewState) {
    @AssistedInject.Factory
    interface Factory :
        AssistedSavedStateViewModelFactory<@JvmSuppressWildcards SchedulerViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): SchedulerViewModel
    }
    fun handleListenForChanges(){
        listenForChanges()
    }
    private fun listenForChanges(){
        viewState.listener = true
        updateUi()
    }
     fun checkSignIn() {
        if (firebaseRepository.user() == null) {
            viewState.newFragment = true
            updateUi()
        }
    }
    fun handleAuthAdmin(): Boolean{
       return authAdmin()
    }
   private fun authAdmin(): Boolean{
      viewModelScope.launch{
          val flow = firebaseRepository
              .isAdmin()
          flow.collect { result: Result<Boolean> ->
              when{
                  result.isSuccess->{
                      viewState.listener = false
                      viewState.newFragment = false
                      viewState.admin= result.isSuccess
                      updateUi()
                  }
                  result.isFailure->{
                      viewState.newFragment = false
                      viewState.admin= false
                      viewState.listener = false
                      viewState.errorMessage =result.exceptionOrNull()?.message!!
                      updateUi()
                  }
              }

          }

      }
       return  viewState.admin
  }

    fun handleCheckSchedule(darasa: String,
                            day: String){
        checkSchedule(darasa, day)
    }
   private fun checkSchedule(darasa: String,
                      day: String){
        if(darasa.isEmpty()){
            Timber.e("Class field empty")
            viewState.listener = false
            updateUi()
            return
        }
       if(day.isEmpty()){
           Timber.e("Day field Empty")
           viewState.listener = false
           updateUi()
           return
       }
        viewState.errorMessage = ""
        updateUi()
        viewModelScope.launch {
            val flow = firebaseRepository.observeSchedule(darasa, day)
            flow.collect { result: Result<List<Schedule?>?> ->
                when{
                    result.isSuccess->{
                        viewState.listener = false
                        viewState.itemsLiveData = firebaseRepository.repoLiveData()
                        updateUi()
                    }
                    result.isFailure->{
                        viewState.listener = false
                       Timber.e(result.exceptionOrNull()?.message!!)
                    }

                }

            }

        }
    }

}