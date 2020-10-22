package com.chuo.timetable.timetabler

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*


class Tarehe(private val editText: EditText?, private val ctx: Context)
    : View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener,View.OnClickListener {
    override fun onClick(v: View?) {
        if(v==editText) {
            datePickerDialog!!.show()
        }
    }
    private val myCalendar = Calendar.getInstance()
    private var datePickerDialog: DatePickerDialog? = null
    val myFormat = "yyyy-MM-dd" //In which you need put here
    val sdformat = SimpleDateFormat(myFormat, Locale.getDefault())

    init {
        editText?.onFocusChangeListener = this
    }
    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        // this.editText.setText();
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        editText?.setText(sdformat.format(myCalendar.time))

    }
        fun setDate() {
            editText?.setOnClickListener(this)
            datePickerDialog = DatePickerDialog(
                ctx,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    myCalendar.set(year, monthOfYear, dayOfMonth)
                    editText?.setText(sdformat.format(myCalendar.time))
                },
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
        }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (hasFocus) {
            DatePickerDialog(ctx, this, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

}