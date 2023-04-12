package de.hhn.softwarelabor.pawswipeapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var pickedDayOfMonth: Int = 0
    private var pickedMonth: Int = 0
    private var pickedYear: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(),this,year,month,day)
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        pickedDayOfMonth = dayOfMonth
        pickedMonth = month
        pickedYear = year
    }

    fun getPickedDayOfMonth(): Int{
        return pickedDayOfMonth
    }

    fun getPickedMonth(): Int{
        return pickedMonth
    }

    fun getPickedYear(): Int{
        return pickedYear
    }

    override fun toString(): String {
       super.toString()

        return "${getPickedDayOfMonth()}.${getPickedMonth()}.${getPickedYear()}"
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        activity?.findViewById<Button>(R.id.petBirthdayButton)?.text = toString()
    }


}