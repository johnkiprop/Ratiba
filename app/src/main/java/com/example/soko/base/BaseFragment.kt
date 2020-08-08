package com.example.soko.base


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

import com.example.soko.di.Injector
import com.example.soko.di.Injector.inject

import com.example.soko.viewmodel.InjectingSavedStateViewModelFactory
import javax.inject.Inject

abstract class BaseFragment<T : ViewBinding>  : Fragment(),
    HasDefaultViewModelProviderFactory {
     protected open var binding: T? = null

    @Inject protected lateinit var abstractFactory:  InjectingSavedStateViewModelFactory
    /**
     * This method androidx uses for `by viewModels` method.
     * We can set out injecting factory here and therefore don't touch it again
     */
    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory =
        abstractFactory.create(this, arguments)

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
        //i.e alternatively a better way

        if (!activity?.isChangingConfigurations!!) {
            Injector.clearComponent(this);
        }
    }


}