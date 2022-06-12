package xyz.daijoubuteam.foodshoppingappadmin.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Eatery(
    @DocumentId
    var id: String? = null,
    var name: String? = null,
    var addressEatery: EateryAddress? = null,
    var work_time: String? = null,
    val average_rating_count: Double? = null,
    var description: String? = null,
    var photoUrl: String? = null,
    val username: String? = null,
    val password: String? = null,
    var eateryId: String? = null,
    @Exclude
    val products: ArrayList<Product>? = null,
    @Exclude
    var eateryPath: @RawValue DocumentReference? = null
) : Parcelable