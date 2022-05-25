package xyz.daijoubuteam.foodshoppingappadmin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Eatery (
    var id: String?=null,
    var name: String?=null,
    var addressEatery: String?=null,
    var work_time: String?=null,
    val average_rating_count: Double?=null,
    var description: String?=null,
    val reviews: ArrayList<String>?=null,
    val products: ArrayList<Product>?=null,
    var photoUrl: String?=null,
    val username: String?=null,
    val password: String?=null,
): Parcelable