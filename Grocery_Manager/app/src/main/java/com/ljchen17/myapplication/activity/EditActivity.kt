package com.ljchen17.myapplication.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ljchen17.myapplication.R
import com.ljchen17.myapplication.fragment.GroceryDetailsFragment
import com.ljchen17.myapplication.model.GroceryDetails
import kotlinx.android.synthetic.main.activity_edit.*
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

    var textViewDate: TextView? = null
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        // get the references from layout file
        textViewDate = this.expirationDate as TextView?

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        textViewDate!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@EditActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })

        // access the items of the list
        val categories = resources.getStringArray(R.array.category)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.category)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, categories)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
            val selectedItemText = parent.getItemAtPosition(position);
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        val grocery = intent.getParcelableExtra<GroceryDetails>(ITEM_TO_EDIT)
        if (grocery != null) {
            itemName.text = Editable.Factory.getInstance().newEditable(grocery.itemName)
            quantity.text = Editable.Factory.getInstance().newEditable(grocery.quantity.toString())
            expirationDate?.text = grocery.expiration
            //category.text = grocery!!.category
            spinner.setSelection(categories.indexOf(grocery.category))
            description?.text = Editable.Factory.getInstance().newEditable(grocery.description)
        }
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textViewDate!!.text = sdf.format(cal.getTime())
    }

    companion object {
        val TAG: String = EditActivity::class.java.simpleName
        const val ITEM_TO_EDIT = "item_to_edit"
    }
}
