package com.chuo.timetable.timetabler

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.chuo.timetable.base.BaseViewModel
import com.chuo.timetable.login.LoginViewState
import com.chuo.timetable.model.Teacher
import com.chuo.timetable.model.TeacherMail
import com.chuo.timetable.repository.FirebaseRepository
import com.chuo.timetable.viewmodel.AssistedSavedStateViewModelFactory
import com.google.firebase.firestore.DocumentReference
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
class TimetablerViewModel @AssistedInject
constructor(firebaseRepository: FirebaseRepository,
            viewState: TimetablerViewState,
            @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel<TimetablerViewState>(firebaseRepository, viewState) {

    @AssistedInject.Factory
    interface Factory :
        AssistedSavedStateViewModelFactory<@JvmSuppressWildcards TimetablerViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): TimetablerViewModel
    }

    private fun validate(darasa: String, day: String, timeStarted: String, timeEnded: String, date: String,
                                 subject: String, teacher: String): Boolean{
        if(darasa.isEmpty() || day.isEmpty() || timeStarted.isEmpty() || timeEnded.isEmpty()||
            date.isEmpty() ||subject.isEmpty() || teacher.isEmpty()){
            this.viewState.errorMessage  = "Please fill out all the fields"
            viewState.updatedSchedule = false
            viewState.submitEnabled = true
            viewState.submitFinishEnabled = true
            updateUi()
            return false
        }

        viewState.errorMessage = ""
        return true
    }
    fun handleGetTeacherItems(){
        getTeacherItems()
    }
    private fun getTeacherItems(){
        viewModelScope.launch{
            val flow = firebaseRepository
                .getTeachers()
            flow.collect { result: Result<List<Teacher>> ->
                when{
                    result.isSuccess->{
                        viewState.teachersLiveData = firebaseRepository.teacherLiveData
                        updateUi()
                    }
                    result.isFailure->{
                        viewState.errorMessage =result.exceptionOrNull()?.message!!
                        updateUi()
                    }
                }

            }

        }
    }
    fun handleTeacherMail(){
        teacherMail()
    }
    private fun teacherMail(){
        viewModelScope.launch {
            val flow = firebaseRepository.observeTutorMail()
            flow.collect { result: Result<List<TeacherMail?>?> ->
                when{
                    result.isSuccess->{
                        viewState.teachersMailList = firebaseRepository.teacherMails
                        updateUi()
                    }
                    result.isFailure->{
                        Timber.e(result.exceptionOrNull()?.message!!)
                    }

                }

            }

        }
    }
    fun handleSchedule(darasa: String, day: String, timeStarted: String, timeEnded:String, date:String,
                       subject: String, teacher: String){
        schedule(darasa, day, timeStarted, timeEnded, date,subject, teacher)
    }

    private fun schedule(darasa: String,
                         day: String,
                         timeStarted: String, timeEnded:String,
                         date:String,
                         subject: String,
                         teacher: String){


        if (!validate(darasa,day,  timeStarted, timeEnded, date, subject, teacher)) {
            updateUi()
            return
        }
        viewState.updatedMessage =""
        viewState.errorMessage = ""
        viewState.updatedSchedule = false
        viewState.submitEnabled = false
        viewState.submitFinishEnabled = false
        updateUi()
        var teacherMail = ""
        for (itemMail in viewState.teachersMailList){
            if(itemMail?.name == teacher) teacherMail = itemMail.email.toString()
        }

        if (teacherMail.isEmpty()){
            viewState.errorMessage = "Could not complete request. Check network connection and try again"
            viewState.updatedSchedule = false
            viewState.submitEnabled = true
            viewState.submitFinishEnabled = true
            updateUi()
            return
        }
        val startTime = "${date}T${timeStarted}:00"
        val endTime = "${date}T${timeEnded}:00"
        val description ="$teacher($subject) - $darasa"
        val item = hashMapOf(
            "description" to description,
            "startTime" to startTime,
            "endTime" to endTime,
            "eventName" to "$darasa Scheduled Class",
            "teacherMail" to teacherMail
            )
        val scheduleItem = hashMapOf(
            "description" to description,
            "startTime" to timeStarted,
            "endTime" to timeEnded,
            "eventName" to "$darasa Scheduled Class",
            "teacherMail" to teacherMail
        )

        viewModelScope.launch {
            val flow = firebaseRepository
                .updateSchedule(darasa, day, scheduleItem)
            flow.collect {result: Result<DocumentReference> ->
                when{
                    result.isSuccess->{
                        viewState.updatedMessage ="Time Slot Updated"
                        viewState.scheduleList.add("$darasa($day)-> $teacher($subject) -$timeStarted")
                        viewState.updatedSchedule = true
                        viewState.submitEnabled = true
                        viewState.submitFinishEnabled = true
                        updateUi()
                    }
                    result.isFailure->{
                        viewState.updatedMessage =""
                        viewState.updatedSchedule = false
                        viewState.submitEnabled = true
                        viewState.submitFinishEnabled = true
                        viewState.errorMessage =result.exceptionOrNull()?.message!!
                        updateUi()
                    }

                }

            }

        }
        viewModelScope.launch {
            val flow = firebaseRepository
                .updateCalendar(item)
            flow.collect {result: Result<DocumentReference> ->
                when{
                    result.isSuccess->{
                   Timber.d("Calendar items updated")
                    }
                    result.isFailure->{
                        Timber.e(result.exceptionOrNull()?.message!!)
                    }

                }

            }
        }
    }

}