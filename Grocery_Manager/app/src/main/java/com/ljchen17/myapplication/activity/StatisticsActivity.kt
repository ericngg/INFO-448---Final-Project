package com.ljchen17.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ljchen17.myapplication.GroceryManagerApp
import com.ljchen17.myapplication.R
import com.ljchen17.myapplication.data.GroceryViewModel
import com.ljchen17.myapplication.data.model.GroceryDetails
import kotlinx.android.synthetic.main.activity_statistics.*

class StatisticsActivity : AppCompatActivity() {

    lateinit var groceryManagerApp: GroceryManagerApp

    private var id: Long = 0

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(GroceryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        //groceryManagerApp = applicationContext as GroceryManagerApp
        //groceryManagerApp.databaseWorkerManager.uploadData()
        //groceryManagerApp.databaseWorkerManager.downloadData()

        // Create the observer which updates the UI.
        val statsObserver = Observer<List<GroceryDetails>> { newData ->
            tvShowData.text = newData.toString()
            Log.i("database42", newData.toString())
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.allGroceries.observe(this, statsObserver)

        btnUpload.setOnClickListener {
            id++
            viewModel.newItem(GroceryDetails(id, null, "NAME $id", 0, "EXP_DATE", "CATEGORY", "DESC"))
        }
    }
}
