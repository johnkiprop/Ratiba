package com.chuo.timetable.scheduler

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.chuo.timetable.R
import com.chuo.timetable.base.BaseFragment
import com.chuo.timetable.databinding.SchedulerScreenBinding
import com.chuo.timetable.model.Schedule
import com.chuo.timetable.viewmodel.InjectingSavedStateViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import java.util.*
import javax.inject.Inject


@ExperimentalCoroutinesApi
class SchedulerFragment : BaseFragment<SchedulerViewModel, SchedulerViewState, SchedulerScreenBinding>(){
    private val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    private val classItems = listOf("Form One", "Form Two", "Form Three")
    @Inject
    lateinit var abstractFactory: InjectingSavedStateViewModelFactory
    private  val repoAdapter = RepoAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //default viewmodel initialization
        val defArgs = bundleOf("id" to null)
        val factory = abstractFactory.create(this, defArgs)
        viewModel = ViewModelProvider(this, factory)[SchedulerViewModel::class.java]
    }
    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): SchedulerScreenBinding=
        SchedulerScreenBinding.inflate(inflater, container, false)
    //root instance when instantiating from launcher
     companion object{
        fun newInstance(): Fragment {
            val bundle = Bundle()
            bundle.putString("instance_id", UUID.randomUUID().toString())
            val fragment: Fragment = SchedulerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewBound(view: View) {
        viewModel.checkSignIn()
        if(!viewModel.checkEmailVerification()){
            Snackbar.make(
                requireView(),
                getString(R.string.get_email),
                Snackbar.LENGTH_SHORT
            ).show()
            viewModel.handleSignOut()
            screenNavigator.goToLogin()
        }
        handleBottomNav()
        viewModel.handleAuthAdmin()
        dropDowns(classItems, binding?.textInputLayoutSelectClass?.editText)
        dropDowns(days, binding?.textInputLayoutSelectDay?.editText)

        // Set up the RecyclerView
        binding?.recyclerView?.setHasFixedSize(true)
        binding?.recyclerView?.layoutManager = GridLayoutManager(context, 2)
        binding?.recyclerView?.adapter= repoAdapter

        binding?.textInputLayoutSelectClass?.editText?.doOnTextChanged { _, _, _, _ ->
            // Respond to input text change
            viewModel.handleListenForChanges()
        }
        binding?.textInputLayoutSelectDay?.editText?.doOnTextChanged { _, _, _, _ ->
            viewModel.handleListenForChanges()

        }

        }


    override fun updateUi(state: SchedulerViewState) {
        if(state.newFragment){
            screenNavigator.goToLogin()
        }
        if(state.listener){
            viewModel.handleCheckSchedule(
                binding?.textInputLayoutSelectClass?.editText?.text.toString(),
                binding?.textInputLayoutSelectDay?.editText?.text.toString())
        }

        if(state.errorMessage.isNotEmpty()){
            Snackbar.make(
                requireView(),
                state.errorMessage,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        state.itemsLiveData?.observe(viewLifecycleOwner, Observer {
            populateCards(it)
        })

    }

    private fun populateCards(listSchedule: List<Schedule>){
          repoAdapter.setData(listSchedule)
    }
    private  fun handleBottomNav(){
        binding?.bottomAppBar?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sign_out -> {
                    viewModel.handleSignOut()

                    // Handle more item (inside overflow menu) press
                    true
                }
                R.id.change_timetable-> {
                    if (viewModel.handleAuthAdmin()) {
                        screenNavigator.goToTimetabler()
                    }else{
                        Snackbar.make(
                            requireView(),
                            getString(R.string.only_admin_allowed),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    true
                }
                else -> false
            }
        }
    }
    private fun dropDowns(myList: List<String>, editText: EditText?){
        val adapter = ArrayAdapter(requireContext(), R.layout.my_cards, myList)
        ( editText as? AutoCompleteTextView)?.setAdapter(adapter)

    }
}