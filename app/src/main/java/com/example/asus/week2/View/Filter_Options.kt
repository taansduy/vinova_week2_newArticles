package com.example.asus.week2.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.asus.week2.R
import com.example.asus.week2.Utils.onSaveFilterListener
import kotlinx.android.synthetic.main.filter_fragment_dialog.*
import kotlinx.android.synthetic.main.item_article_search_layout.*
import android.databinding.adapters.TextViewBindingAdapter.setText
import java.text.SimpleDateFormat
import java.util.*
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.content.Context



@SuppressLint("ValidFragment")
class Filter_Options(private val mcontext: Context,private val mdate: String?,private val sort:String?,private val newDesk:String?): DialogFragment(){

    var myCalendar = Calendar.getInstance()
    var listener: onSaveFilterListener?=null
    var date: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.filter_fragment_dialog, container);

    }
    fun setSaveListener(clickListener: onSaveFilterListener)
    {
        listener=clickListener
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(mdate!=null && !mdate.equals(""))
        {
            var year=mdate.substring(0,4)
            var month=mdate.substring(4,6)
            var day=mdate.substring(6,8)
            myCalendar.set(year.toInt(),month.toInt(),day.toInt());
            updateLabel()
        }
        if(sort=="Oldest")
        {
            spinner.setSelection(0)
        }
        else
        {
            spinner.setSelection(1)
        }
        if(newDesk!=null)
        {
            if(newDesk!!.contains("Arts"))
            {
                checkBox.isChecked=true
            }
            if(newDesk!!.contains("Fashion"))
            {
                checkBox2.isChecked=true
            }
            if(newDesk!!.contains("Sports"))
            {
                checkBox3.isChecked=true
            }
        }

        editText.setOnClickListener{
            DatePickerDialog(mcontext
                , date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        btnSave.setOnClickListener{
            var date=editText.text.toString().replace("/","")
            var sort=spinner.selectedItem.toString()
            var newdesk=""
            if(checkBox.isChecked)
            {
                newdesk+=" "+"\"Arts\""
            }
            if(checkBox2.isChecked)
            {
                newdesk+=" "+"\"Fashion & Style\""
            }
            if(checkBox3.isChecked)
            {
                newdesk+=" "+"\"Sports\""
            }
            listener?.onItemClick(date,sort,newdesk)
        }
    }

    private fun updateLabel() {
        val myFormat = "yyyy/MM/dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        editText.setText(sdf.format(myCalendar.getTime()))
    }
}