package de.hhn.softwarelabor.pawswipeapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var pickedDayOfMonth: Int = 0
    private var pickedMonth: Int = 0
    private var pickedYear: Int = 0
    private var dateFormat: String = ""
    private lateinit var onDatePickedListener: (String) -> Unit
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        dateFormat = getString(R.string.dateFormat)

        return DatePickerDialog(requireContext(),this,year,month,day)
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        pickedDayOfMonth = dayOfMonth
        pickedMonth = month
        pickedYear = year
    }

    private fun getPickedDayOfMonth(): Int{
        return pickedDayOfMonth
    }

    private fun getPickedMonth(): Int {

        return pickedMonth
    }

    private fun getPickedYear(): Int{
        return pickedYear
    }

    override fun toString(): String {
       super.toString()


        try {
            val newCalendar = Calendar.getInstance()

            newCalendar.set(getPickedYear(), getPickedMonth(), getPickedDayOfMonth())

            val date = newCalendar.time

            val format = SimpleDateFormat(dateFormat, Locale.getDefault())
            return format.format(date)
        }catch (e: NullPointerException){
            e.printStackTrace()
        }catch (e: IllegalArgumentException){
            e.printStackTrace()
        }

        return ""
    }

    override fun onDismiss(dialog: DialogInterface)  {
        super.onDismiss(dialog)
        onDatePickedListener(toString())

    }

    fun setOnDatePickedListener(date: (String) -> Unit){
        onDatePickedListener = date
    }


}