package com.chuo.timetable.newuser

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chuo.timetable.R
import com.chuo.timetable.base.BaseFragment
import com.chuo.timetable.databinding.NewTeacherScreenBinding
import androidx.lifecycle.Observer
import com.chuo.timetable.model.Teacher
import com.chuo.timetable.viewmodel.InjectingSavedStateViewModelFactory
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class NewTeacherFragment: BaseFragment<NewTeacherViewModel,NewTeacherViewState, NewTeacherScreenBinding>(){
    @Inject lateinit var abstractFactory: InjectingSavedStateViewModelFactory
    private var teachers : List<Teacher> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val defArgs = bundleOf("id" to null)
        val factory = abstractFactory.create(this, defArgs)
        viewModel = ViewModelProvider(this, factory)[NewTeacherViewModel::class.java]
    }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): NewTeacherScreenBinding =
        NewTeacherScreenBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance(): Fragment {
            val bundle = Bundle()
            bundle.putString("instance_id", UUID.randomUUID().toString())
            val fragment: Fragment = NewTeacherFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewBound(view: View) {
        viewModel.teacherList()
        binding?.textViewNewTeacherLogin?.setOnClickListener {
            screenNavigator.goToLogin()
        }
   binding?.button?.setOnClickListener {
       if(teachers.size < 3) {
           viewModel.handleSubmitButtonClicked(
               binding?.textInputLayout2?.editText?.text.toString(),
               binding?.textiputFirstname?.editText?.text.toString(),
               binding?.textinputSecondname?.editText?.text.toString()
           )
       }
   }

    }

    override fun updateUi(state: NewTeacherViewState) {

        state.teachersLiveData?.observe(this, Observer {
            setTeacherChips(it)
            teachers = it
        })

        if(teachers.size == 3){
            screenNavigator.goToTimetabler()
        }
        binding?.button?.isEnabled = state.submitEnabled
        if (teachers.size == 2){
            Snackbar.make(
                requireView(),
                "Create Scheduler/Timetabler account",
                Snackbar.LENGTH_SHORT
            ).show()
            binding?.button?.text = getString(R.string.create_other_accounts)
        }

        if (state.progress){
            Snackbar.make(
                requireView(),
                getString(R.string.success_account_created),
                Snackbar.LENGTH_SHORT
            ).show()
        }
        if (state.ackAdmin.isNotEmpty()){
            Snackbar.make(
                requireView(),
                state.ackAdmin,
                Snackbar.LENGTH_SHORT
            ).show()
        }
        if(state.errorMessage.isNotEmpty()){
            Snackbar.make(
                requireView(),
                state.errorMessage,
                Snackbar.LENGTH_SHORT
            ).show()
        }
        if(state.progress && state.errorMessage.isEmpty()){
            viewModel.handleDoOnCreateUser(
                binding?.textiputFirstname?.editText?.text.toString(),
                binding?.textinputSecondname?.editText?.text.toString(),
                teachers
            )
            viewModel.handleCreateTeacherMail(
                binding?.textInputLayout2?.editText?.text.toString(),
                binding?.textiputFirstname?.editText?.text.toString(),
                binding?.textinputSecondname?.editText?.text.toString()
            )
        }
    }
   private fun setTeacherChips(teachers:List<Teacher>) {
       binding?.chipsTeachers?.removeAllViews()
        for (teacherName in teachers) {
            val mChip =
                this.layoutInflater.inflate(R.layout.item_chips_teachers, null, false) as Chip
            mChip.text = teacherName.name
            val paddingDp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10f,
                resources.displayMetrics
            ).toInt()
            mChip.setPadding(paddingDp, 0, paddingDp, 0)
            mChip.setOnCheckedChangeListener { compoundButton, b -> }
            binding?.chipsTeachers?.addView(mChip)
        }
    }

}