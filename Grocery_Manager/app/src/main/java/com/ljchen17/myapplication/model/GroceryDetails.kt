package com.ljchen17.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GroceryDetails (
    val itemName: String,
    val quantity: Int,
    val expiration: String,
    val category: String,
    val description: String
): Parcelable
