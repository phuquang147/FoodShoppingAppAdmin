package xyz.daijoubuteam.foodshoppingappadmin.model

import android.os.Parcelable

enum class Gender(val gender: String? = "male") {
    MALE("male"),
    FEMALE("female"),
    OTHER("other")
}