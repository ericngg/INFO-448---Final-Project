package com.ljchen17.myapplication.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AAChartFontWeightType
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AAChartModel
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AAChartType
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AASeriesElement
import com.aachartmodel.aainfographics.AAInfographicsLib.AAOptionsModel.AAChart
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
                .chartType(AAChartType.Pie)
                .backgroundColor("#303030")
                .titleFontColor("#ffffff")
                .axesTextColor("#ffffff")
                .colorsTheme(arrayOf("#E1162E"))
                .tooltipEnabled(false)
                .tooltipCrosshairs(false)
                .legendEnabled(false)
                .series(arrayOf(AASeriesElement().data(updateGraph(newData))))

            AAChartView?.aa_drawChartWithChartModel(aaChartModel)

            Log.i("database42", newData.toString())
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.allGroceries.observe(this, statsObserver)

//        val aaChartModel = AAChartModel()
//            .chartType(AAChartType.Pie)
//            .title("Types of Expired Food")
//            .subtitle("subtitle")
//            .backgroundColor("#303030")
//            .titleFontColor("#ffffff")
//            .axesTextColor("#ffffff")
//            .subtitleFontColor("#ffffff")
//            .tooltipEnabled(false)
//            .tooltipCrosshairs(false)
//            .legendEnabled(false)
//            .colorsTheme(arrayOf("#E1162E"))
//            .series(arrayOf(
//                AASeriesElement()
//                    .data(arrayOf(arrayOf("Grain", 6.9),
//                                  arrayOf("Deli", 0.8)))
//            )
//            )
//
//        AAChartView?.aa_drawChartWithChartModel(aaChartModel)
    }

    private fun updateGraph(rawData: List<GroceryDetails>): Array<Any> {
        val dataCategories = linkedMapOf<String, Int>()
        for (entry in rawData) {
            if(dataCategories.containsKey(entry.category)) {
                val newValue = dataCategories.getValue(entry.category) + entry.quantity
                dataCategories[entry.category] = newValue
            } else {
                dataCategories[entry.category] = (entry.quantity)
            }
        }

        val formattedData = mutableListOf<Array<Any>>()
        for (entry in dataCategories) {
            formattedData.add(arrayOf(entry.key, entry.value))
        }

        return formattedData.toTypedArray()
    }


//    private fun updateGraph(rawData: List<GroceryDetails>): Array<AASeriesElement> {
//        val dataCategories = linkedMapOf<String, Float>()
//        for (entry in rawData) {
//            if(dataCategories.containsKey(entry.category)) {
//                val newValue = dataCategories.getValue(entry.category) + entry.quantity
//                dataCategories[entry.category].
//            } else {
//                dataCategories[entry.category] = entry.quantity + 0F
//            }
//        }
//
//        val seriesElements = mutableListOf<AASeriesElement>()
//        for (entry in dataCategories) {
//            seriesElements.add(AASeriesElement().name(entry.key).data(arrayOf(entry.key, entry.value)))
//        }
//
//        return seriesElements.toTypedArray()
//    }

//    private fun updateGraph(rawData: List<GroceryDetails>): Array<AASeriesElement> {
//        val dataCategories = linkedMapOf<String, Int>()
//        for (entry in rawData) {
//            if(dataCategories.containsKey(entry.category)) {
//                val newValue = dataCategories.getValue(entry.category) + entry.quantity
//                dataCategories[entry.category] = newValue
//            } else {
//                dataCategories[entry.category] = (entry.quantity)
//            }
//        }
//
//        val seriesElements = mutableListOf<AASeriesElement>()
//        for (entry in dataCategories) {
//            seriesElements.add(AASeriesElement().name(entry.key).data(arrayOf(entry.value)))
//        }
//
//        return seriesElements.toTypedArray()
//    }

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
