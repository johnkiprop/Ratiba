package com.chuo.timetable.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chuo.timetable.R
import com.chuo.timetable.base.BaseFragment
import com.chuo.timetable.databinding.LoginScreenBinding
import com.chuo.timetable.di.ScreenScope
import com.chuo.timetable.viewmodel.InjectingSavedStateViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoginFragment : BaseFragment<LoginViewModel,LoginViewState, LoginScreenBinding>(){
    @Inject lateinit var abstractFactory: InjectingSavedStateViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val defArgs = bundleOf("id" to null)
        val factory = abstractFactory.create(this, defArgs)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }
    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): LoginScreenBinding=
        LoginScreenBinding.inflate(inflater, container, false)

    companion object{
       fun newInstance(): Fragment {
           val bundle = Bundle()
           bundle.putString("instance_id", UUID.randomUUID().toString())
           val fragment: Fragment = LoginFragment()
           fragment.arguments = bundle
           return fragment
       }
    }
    override fun onViewBound(view: View) {
    binding?.button2?.setOnClickListener {
        viewModel.handleSubmitButtonClicked(
            binding?.textInputLayoutLoginEmail?.editText?.text.toString(),
            binding?.textInputLayoutLoginPassword?.editText?.text.toString()
        )

    }
        binding?.textViewGoToCreateAc?.setOnClickListener {
            screenNavigator.goToNewTeacher()
        }
        binding?.textViewChangePassword?.setOnClickListener {
            viewModel.handleChangePass(
                binding?.textInputLayoutLoginEmail?.editText?.text.toString()
            )
        }
    }

     override fun updateUi(state: LoginViewState) {
         binding?.button2?.isEnabled = state.submitEnabled
         if(state.changePassMessage.isNotEmpty()){
             Snackbar.make(
                 requireView(),
                 state.changePassMessage,
                 Snackbar.LENGTH_LONG
             ).show()
         }
         if(state.errorMessage.isNotEmpty()){
             Snackbar.make(
                 requireView(),
                 state.errorMessage,
                 Snackbar.LENGTH_LONG
             ).show()
         }

         if(state.progress){
             if(!viewModel.checkEmailVerification()){
                 Snackbar.make(
                     requireView(),
                     getString(R.string.get_email),
                     Snackbar.LENGTH_SHORT
                 ).show()
             }else{
                 screenNavigator.goToScheduler()
             }
         }
     }


 }