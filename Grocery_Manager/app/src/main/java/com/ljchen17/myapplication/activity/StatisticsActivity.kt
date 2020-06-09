package com.ljchen17.myapplication.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AAChartModel
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AAChartType
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AASeriesElement
import com.ljchen17.myapplication.R
import com.ljchen17.myapplication.data.GroceryViewModel
import com.ljchen17.myapplication.data.model.GroceryDetails
import kotlinx.android.synthetic.main.activity_statistics.*

class StatisticsActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(GroceryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        theme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        // Create the observer which updates the UI.
        val statsObserver = Observer<List<GroceryDetails>> { newData ->
            val aaChartModel = AAChartModel()
                .chartType(AAChartType.Column)
                .title("Types of Food")
                .series(updateGraph(newData))

            AAChartView?.aa_drawChartWithChartModel(aaChartModel)

            Log.i("database42", newData.toString())
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.allGroceries.observe(this, statsObserver)

//        val aaChartModel = AAChartModel()
//            .chartType(AAChartType.Area)
//            .title("title")
//            .subtitle("subtitle")
//            .backgroundColor("#4b2b7f")
//            .yAxisGridLineWidth(0F)
//            .series(arrayOf(
//                AASeriesElement()
//                    .name("Tokyo")
//                    .data(arrayOf(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6)),
//                AASeriesElement()
//                    .name("NewYork")
//                    .data(arrayOf(0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5)),
//                AASeriesElement()
//                    .name("London")
//                    .data(arrayOf(0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0)),
//                AASeriesElement()
//                    .name("Berlin")
//                    .data(arrayOf(3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8))
//            )
//            )
    }

    private fun updateGraph(rawData: List<GroceryDetails>): Array<AASeriesElement> {
        val dataCategories = linkedMapOf<String, Float>()
        for (entry in rawData) {
            if(dataCategories.containsKey(entry.category)) {
                val newValue = dataCategories.getValue(entry.category) + entry.quantity
                dataCategories[entry.category] = newValue
            } else {
                val float = entry.quantity + 0F
                dataCategories[entry.category] = (float)
            }
        }

        val seriesElements = mutableListOf<AASeriesElement>()
        for (entry in dataCategories) {
            seriesElements.add(AASeriesElement().name(entry.key).data(arrayOf(entry.value)))
        }

        return seriesElements.toTypedArray()
    }

    @SuppressLint("ResourceAsColor")
    private fun theme() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val name = sharedPreferences.getBoolean("theme",  true)
        if (name) {
            setTheme(R.style.dark)
        } else {
            setTheme(R.style.AppTheme)
        }
    }
}
