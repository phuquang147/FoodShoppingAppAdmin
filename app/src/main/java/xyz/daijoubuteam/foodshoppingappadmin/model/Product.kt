package xyz.daijoubuteam.foodshoppingappadmin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product (
    var id: String?=null,
    val name: String?=null,
    val description: String?=null,
    val oldPrice: Double ?= null,
    val newPrice: Double?=null,
    var img: String?=null,
    val ingredients: ArrayList<String>?=null,
): Parcelable