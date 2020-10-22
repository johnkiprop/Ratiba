package com.chuo.timetable.timetabler

import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chuo.timetable.R
import com.chuo.timetable.base.BaseFragment
import com.chuo.timetable.databinding.TimetablerScreenBinding
import com.chuo.timetable.viewmodel.InjectingSavedStateViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*
import javax.inject.Inject
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip

@ExperimentalCoroutinesApi
class TimetablerFragment : BaseFragment<TimetablerViewModel, TimetablerViewState, TimetablerScreenBinding>() {
    @Inject lateinit var abstractFactory: InjectingSavedStateViewModelFactory
    private val subjectItems = listOf("Math", "Physics", "Eng", "Swa", "Bio", "Art", "Geo", "CRE")
    private val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    private val classItems = listOf("Form One", "Form Two", "Form Three")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val defArgs = bundleOf("id" to null)
        val factory = abstractFactory.create(this, defArgs)
        viewModel = ViewModelProvider(this, factory)[TimetablerViewModel::class.java]
    }
    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): TimetablerScreenBinding=
        TimetablerScreenBinding.inflate(inflater, container, false)

    companion object{
        fun newInstance(): Fragment {
            val bundle = Bundle()
            bundle.putString("instance_id", UUID.randomUUID().toString())
            val fragment: Fragment = TimetablerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewBound(view: View) {
        viewModel.handleTeacherMail()
        dropDowns(subjectItems, binding?.selectSubject?.editText)
        dropDowns(classItems, binding?.selectClass?.editText)
        dropDowns(days, binding?.selectDay?.editText)
        //set the date and time pickers
        val meetingDate = Tarehe(binding?.selectDates?.editText, requireContext())
        meetingDate.setDate()
        TimeSetter(binding?.selectTimeSlots?.editText)
        TimeSetter(binding?.selectTimeSlots2?.editText)
        disableSoftInputFromAppearing(binding?.selectTimeSlots?.editText)
        disableSoftInputFromAppearing(binding?.selectTimeSlots2?.editText)
        //handle viewmodel calls
        viewModel.handleGetTeacherItems()
       binding?.buttonFinishScheduling?.setOnClickListener {
         if(viewModel.checkEmailVerification()){
             screenNavigator.goToScheduler()
         }
           else{
             Snackbar.make(
                 requireView(),
                 "Verify Email to Proceed",
                 Snackbar.LENGTH_LONG
             ).show()
             screenNavigator.goToLogin()
         }
       }
        binding?.buttonTimetabler?.setOnClickListener {
                viewModel.handleSchedule(
                    binding?.selectClass?.editText?.text.toString(),
                    binding?.selectDay?.editText?.text.toString(),
                    binding?.selectTimeSlots?.editText?.text.toString(),
                    binding?.selectTimeSlots2?.editText?.text.toString(),
                    binding?.selectDates?.editText?.text.toString(),
                    binding?.selectSubject?.editText?.text.toString(),
                    binding?.selectTeacher?.editText?.text.toString()
                )

        }
    }
    override fun updateUi(state: TimetablerViewState) {
        binding?.buttonTimetabler?.isEnabled = state.submitEnabled
        binding?.buttonFinishScheduling?.isEnabled = state.submitFinishEnabled
        if(state.updatedMessage.isNotEmpty()){
            Snackbar.make(
                requireView(),
                state.updatedMessage,
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

        state.teachersLiveData?.observe(this, Observer { teacher ->
            val adapterTeachers = ArrayAdapter(requireContext(), R.layout.my_cards, teacher.map { it.name })
            (binding?.selectTeacher?.editText as? AutoCompleteTextView)?.setAdapter(adapterTeachers)
        })
        setScheduledChips(state.scheduleList)

    }
    fun disableSoftInputFromAppearing(editText: EditText?) {
        editText?.setRawInputType(InputType.TYPE_CLASS_TEXT)
        editText?.setTextIsSelectable(true)
    }
    private fun dropDowns(myList: List<String>, editText: EditText?){
        val adapter = ArrayAdapter(requireContext(), R.layout.my_cards, myList)
        ( editText as? AutoCompleteTextView)?.setAdapter(adapter)

    }

    private fun setScheduledChips(schedules: MutableList<String?>) {
        binding?.chipsScheduled?.removeAllViews()
        for (schedule in schedules.distinct()) {
            val mChip =
                this.layoutInflater.inflate(R.layout.item_chips_teachers, null, false) as Chip
            mChip.text = schedule
            val paddingDp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10f,
                resources.displayMetrics
            ).toInt()
            mChip.setPadding(paddingDp, 0, paddingDp, 0)
            mChip.setOnCheckedChangeListener { compoundButton, b -> }
            binding?.chipsScheduled?.addView(mChip)
        }
    }
}