package com.chuo.timetable.base


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

import com.chuo.timetable.di.Injector
import com.chuo.timetable.di.Injector.inject
import com.chuo.timetable.ui.ScreenNavigator

import com.chuo.timetable.viewmodel.InjectingSavedStateViewModelFactory
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment<V: BaseViewModel<S>, S: ViewState,T : ViewBinding> : Fragment(){
     protected open var binding: T? = null
      protected open lateinit var viewModel: V
    @Inject protected lateinit var screenNavigator: ScreenNavigator

//state functions
    protected open fun onBindViewModel() {
    observeState()
    }
    private fun observeState() {
        viewModel.getState().observe(viewLifecycleOwner, Observer { state -> updateUi(state as S) })
    }

    abstract fun updateUi(state: S)


    override fun onResume() {
        super.onResume()
        onBindViewModel()
    }
    override fun onPause() {
        super.onPause()
        onUnbindViewModel()
    }

    protected open fun onUnbindViewModel() {
        // Empty lifecycle function to be overridden

    }

//    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory =
//        abstractFactory.create(this, arguments)

    override fun onAttach(context: Context) {
        inject(this)
        super.onAttach(context)

    }


    protected open fun onViewBound(view:View) {}

    abstract fun setBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewBound(view)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedViewState: Bundle?
    ): View? {
        this.binding = this.setBinding(inflater,container)
        val view =  this.binding!!.root
        return  view
    }
    override fun onDestroyView() {

        /**
         * To not leak memory we nullify the binding when the view is destroyed.
         */

        binding = null

        super.onDestroyView()

    }

    override fun onDestroy() {
        super.onDestroy()
        //this callback is for when fragment is getting destroyed. Please check if it will not come back

        if (!activity?.isChangingConfigurations!!) {
            Injector.clearComponent(this)
        }
    }



}