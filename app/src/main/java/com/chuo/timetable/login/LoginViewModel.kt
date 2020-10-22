package com.chuo.timetable.login

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.chuo.timetable.base.BaseViewModel
import com.chuo.timetable.repository.FirebaseRepository
import com.chuo.timetable.ui.ScreenNavigator
import com.chuo.timetable.viewmodel.AssistedSavedStateViewModelFactory
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoginViewModel
@AssistedInject
constructor(firebaseRepository: FirebaseRepository,
            viewState: LoginViewState,
            @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel<LoginViewState>(firebaseRepository, viewState) {

    @AssistedInject.Factory
    interface Factory :
        AssistedSavedStateViewModelFactory<@JvmSuppressWildcards LoginViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): LoginViewModel
    }

    fun handleSubmitButtonClicked(username: String, password: String) {
        loginWithEmailAndPassword(username, password)
    }
    fun handleChangePass(email: String){
        changePassword(email)
    }

    private fun changePassword(email: String){
    if(email.isEmpty()){
        viewState.errorMessage = "Fill Email field"
        updateUi()
        return
    }
        viewState.submitEnabled = false
        viewState.errorMessage = ""
        viewState.progress = false
        updateUi()
        viewModelScope.launch {
            firebaseRepository.changePassword(email).collect {result: Result<Task<Void>> ->
                when{
                    result.isSuccess->{
                        viewState.submitEnabled = true
                        viewState.changePassMessage = "Password Changing Email Sent"
                        updateUi()
                    }
                    result.isFailure->{
                        viewState.submitEnabled = true
                        viewState.errorMessage =  result.exceptionOrNull()?.message!!
                        updateUi()
                    }
                }
            }
        }

    }
    private fun loginWithEmailAndPassword(email: String, password: String) {

        if (!validate(email, password)) {
            updateUi()
            return
        }
        viewState.submitEnabled = false
        viewState.progress = false
        updateUi()

            viewModelScope.launch{
                val flow =  firebaseRepository.loginTeacher(email, password)
                flow.collect {result: Result<AuthResult> ->
                    when{
                        result.isSuccess->{
                            viewState.submitEnabled = true
                            viewState.progress = true
                            updateUi()
                        }
                        result.isFailure->{
                            viewState.submitEnabled = true
                            viewState.progress = false
                            viewState.errorMessage =  result.exceptionOrNull()?.message!!
                            updateUi()
                        }

                    }

                }
            updateUi()
        }
    }
    private fun validate(email: String, password: String): Boolean {

        if (email.isEmpty() || password.isEmpty()) {
            this.viewState.errorMessage = "Please fill out all the fields"
            viewState.progress = false
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.viewState.errorMessage = "Please enter a valid email"
            viewState.progress = false
            return false
        }

        if (password.length < 6) {
            this.viewState.errorMessage = "Password must be at least 6 characters long"
            viewState.progress = false
            return false
        }

        viewState.errorMessage = ""
        viewState.progress = false
        return true
    }

}