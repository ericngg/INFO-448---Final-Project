package com.ljchen17.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ljchen17.myapplication.data.model.GroceryDetails


@Dao
interface GroceryDetailsDao {
    @Query("SELECT * from grocerydetails")
    fun getAll(): LiveData<List<GroceryDetails>>

    @Update
    fun update(vararg item: GroceryDetails)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg item: GroceryDetails)

    @Delete
    fun delete(item: GroceryDetails)
}
