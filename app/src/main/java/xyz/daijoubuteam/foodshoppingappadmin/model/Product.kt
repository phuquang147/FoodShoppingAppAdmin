package xyz.daijoubuteam.foodshoppingappadmin.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @DocumentId
    var id: String? = null,
    var name: String? = null,
    var description: String? = null,
    var oldPrice: Double? = null,
    var newPrice: Double? = null,
    var img: String? = null,
    var ingredients: ArrayList<String>? = null,
) : Parcelable