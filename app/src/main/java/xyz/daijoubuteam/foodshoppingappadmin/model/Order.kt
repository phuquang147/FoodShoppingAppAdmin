package xyz.daijoubuteam.foodshoppingappadmin.model

import android.os.Parcelable
import androidx.lifecycle.LiveData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Order(
    @DocumentId
    var id: String? = null,
    val eateryId: @RawValue DocumentReference? = null,
    var orderItems: ArrayList<ProductInOrder>? = null,
    val orderTime: @RawValue Timestamp? = null,
    val totalPrice: Double? = null,
    var status: String? = null,
    val shippingAddress: ShippingAddress ?= null,
    @Exclude
    var orderPath: @RawValue DocumentReference? = null
) : Parcelable