package com.ljchen17.myapplication.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ljchen17.myapplication.data.model.GroceryDetails
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**class DatabaseManager (context: Context){
    val db = Room.databaseBuilder(
        context,
        GroceryDetailsAppDatabase::class.java, "Grocery Database"
    ).build()

    fun getItems(): List<GroceryDetails> {
        return db.groceryDetailsDao().getAll()
    }

    fun updateItem(item: GroceryDetails) {
        return db.groceryDetailsDao().update(item)
    }

    fun newItem(item: GroceryDetails) {
        return db.groceryDetailsDao().insert(item)
    }

    fun deleteItems(item: GroceryDetails) {
        return db.groceryDetailsDao().delete(item)
    }
}**/


class GroceryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GroceryRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allGroceries: LiveData<List<GroceryDetails>>

    init {
        val wordsDao = GroceryDetailsAppDatabase.getDatabase(application, viewModelScope).groceryDetailsDao()
        repository = GroceryRepository(wordsDao)
        allGroceries = repository.allGroceries
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun newItem(item: GroceryDetails) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }

    fun updateItem(item: GroceryDetails) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(item)
    }

    fun deleteItem(item: GroceryDetails) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(item)
    }
}