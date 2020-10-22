package com.chuo.timetable.timetabler

import android.app.TimePickerDialog
import android.view.View
import android.widget.EditText
import android.widget.TimePicker
import java.text.SimpleDateFormat
import java.util.*

class TimeSetter(private val mEditText: EditText?) : View.OnFocusChangeListener, View.OnClickListener, TimePickerDialog.OnTimeSetListener {


    private var mCalendar: Calendar? = null
    private var mFormat: SimpleDateFormat? = null

    init {
        mEditText?.setOnClickListener(this)
    }
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if(hasFocus){
            if (v != null) {
                showPicker(v)
            }
        }
    }
    override fun onClick(view: View) {
        showPicker(view)
    }

    private fun showPicker(view: View) {
        if (mCalendar == null)
            mCalendar = Calendar.getInstance()

        val hour = mCalendar!!.get(Calendar.HOUR_OF_DAY)
        val minute = mCalendar!!.get(Calendar.MINUTE)

        TimePickerDialog(view.context, this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        mCalendar!!.set(Calendar.HOUR_OF_DAY, hourOfDay)
        mCalendar!!.set(Calendar.MINUTE, minute)

        if (mFormat == null)
            mFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        this.mEditText?.setText(mFormat!!.format(mCalendar!!.time))
    }

}
