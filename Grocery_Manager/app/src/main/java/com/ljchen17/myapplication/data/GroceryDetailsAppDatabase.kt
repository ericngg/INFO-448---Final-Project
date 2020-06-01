package com.ljchen17.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ljchen17.myapplication.data.model.GroceryDetails
import kotlinx.coroutines.CoroutineScope

@Database(entities = [GroceryDetails::class], version = 1)
abstract class GroceryDetailsAppDatabase : RoomDatabase() {
    abstract fun groceryDetailsDao(): GroceryDetailsDao

    companion object {
        @Volatile
        private var INSTANCE: GroceryDetailsAppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): GroceryDetailsAppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GroceryDetailsAppDatabase::class.java,
                    "grocery_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }
}