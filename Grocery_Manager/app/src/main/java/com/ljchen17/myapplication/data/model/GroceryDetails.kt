package com.ljchen17.myapplication.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "grocerydetails")
data class GroceryDetails (
    @PrimaryKey var iid: Long,
    @ColumnInfo(name = "image_path") val imagePath: String?,
    @ColumnInfo(name = "item_name") val itemName: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "expiration") val expiration: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "description") val description: String
): Parcelable
