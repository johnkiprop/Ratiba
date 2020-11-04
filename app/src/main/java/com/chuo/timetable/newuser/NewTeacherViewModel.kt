package com.chuo.timetable.newuser

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.chuo.timetable.base.BaseViewModel
import com.chuo.timetable.model.Schedule
import com.chuo.timetable.model.Teacher
import com.chuo.timetable.repository.FirebaseRepository
import com.chuo.timetable.ui.ScreenNavigator
import com.chuo.timetable.viewmodel.AssistedSavedStateViewModelFactory
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

@ExperimentalCoroutinesApi
class NewTeacherViewModel @AssistedInject
    constructor(firebaseRepository: FirebaseRepository,
             viewState: NewTeacherViewState,
             @Assisted private val savedStateHandle: SavedStateHandle
   ) : BaseViewModel<NewTeacherViewState>(firebaseRepository, viewState) {

    @AssistedInject.Factory
    interface Factory : AssistedSavedStateViewModelFactory<@JvmSuppressWildcards NewTeacherViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): NewTeacherViewModel
    }


    fun handleSubmitButtonClicked(username: String,firstName:String, lastName:String ) {
        registerWithEmailAndPassword(username, firstName, lastName)
    }
    fun teacherList(){
        observeTeacherList()
    }
    private  fun observeTeacherList(){
        viewModelScope.launch {
            firebaseRepository.observeTutors().collect { result: Result<List<Teacher>?>->
                when{
                    result.isSuccess ->{
                        viewState.teachersLiveData = firebaseRepository.observeTeacherLiveData()
                        viewState.progress = false
                        updateUi()
                    }
                    result.isFailure ->{
                        Timber.e(result.exceptionOrNull()?.message!!)
                    }
                }
            }
        }
    }
    private fun registerWithEmailAndPassword(username: String,firstName:String, lastName:String) {
        if (!validate(username, firstName, lastName)) {
            updateUi()
            return
        }
        viewState.errorMessage = ""
        viewState.progress = false
        viewState.submitEnabled  = false
        updateUi()


        viewModelScope.launch{
          firebaseRepository.registerTeacher(username).collect {result:Result<AuthResult> ->
                when{
                    result.isSuccess->{
                        firebaseRepository.user()!!.sendEmailVerification()
                        firebaseRepository.auth()?.sendPasswordResetEmail(username)
                        viewState.progress = true
                        viewState.submitEnabled = true
                        updateUi()
                    }
                    result.isFailure->{
                        viewState.progress = false
                        viewState.submitEnabled = true
                        viewState.errorMessage =result.exceptionOrNull()?.message!!
                        updateUi()
                    }
                }
            }
        }


    }
    fun handleCreateTeacherMail(email: String, firstName: String, lastName: String){
        createTeacherMail(email, firstName, lastName)
    }
    private  fun createTeacherMail(email: String, firstName: String, lastName: String){
       val mailMap = hashMapOf(
           "email" to email,
           "name" to "$firstName $lastName"
       )
        viewModelScope.launch {
            firebaseRepository.addTeachersMail(mailMap).collect {result:Result<DocumentReference> ->
                when{
                    result.isSuccess->{
                        Timber.d("Teacher Mail added")
                    }
                    result.isFailure->{
                        viewState.errorMessage =result.exceptionOrNull()?.message!!

                    }

                }
            }
        }
    }
    fun handleDoOnCreateUser(firstName: String, lastName: String,teachers: List<Teacher>){
        doOnCreateUser(firstName, lastName, teachers)

    }
    private fun doOnCreateUser(firstName: String, lastName: String,teachers: List<Teacher>){


            val teacherMap = hashMapOf(
                "name" to "$firstName $lastName"
            )
            viewModelScope.launch {
                firebaseRepository.addTeachersList(teacherMap).collect {result:Result<DocumentReference> ->
                    when{
                        result.isSuccess->{
                            Timber.d("Teacher added")
                        }
                        result.isFailure->{
                            viewState.errorMessage =result.exceptionOrNull()?.message!!

                        }

                    }
                }
            }
        if(teachers.size == 2){
            createAdmin(firstName, lastName)
        }
    }

     private  fun createAdmin(firstName: String, lastName: String){
             viewModelScope.launch {
                 firebaseRepository.addAdmin().collect {result:Result<Void> ->
                     when{
                         result.isSuccess->{
                             viewState.ackAdmin = "User $firstName $lastName is in charge of the timetable"
                             viewState.progress = false
                             updateUi()
                         }
                         result.isFailure->{
                             viewState.progress = false
                             viewState.errorMessage =result.exceptionOrNull()?.message!!
                             updateUi()
                         }

                     }
                 }


         }
     }
    private fun validate(email: String, firstName:String, lastName:String): Boolean {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            this.viewState.errorMessage = "Please fill out all the fields"
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.viewState.errorMessage = "Please enter a valid email"
            return false
        }

        viewState.errorMessage = ""
        viewState.progress = false
        return true
    }

    }


